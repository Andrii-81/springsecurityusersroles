package com.example.springsecurityusersroles.service.impl;

import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.Exception.NotValidDataException;
import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.Status;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.repository.RoleRepository;
import com.example.springsecurityusersroles.repository.UserRepository;
import com.example.springsecurityusersroles.service.RoleService;
import com.example.springsecurityusersroles.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // private final RoleRepository roleRepository;
    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,  RoleService roleService, PasswordEncoder passwordEncoder) { //RoleRepository roleRepository) { //}}, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        // this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDTO> findAllRest() {
        List<User> users = userRepository.findAll();

        List<UserDTO> usersDTO = convertorToDTO(users);
        return usersDTO;
    }

    public List<UserDTO> convertorToDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserDTO tmpUser = new UserDTO();
            tmpUser.setId(user.getId());
            tmpUser.setEmail(user.getEmail());
            tmpUser.setPassword(user.getPassword());
            tmpUser.setFirstName(user.getFirstName());
            tmpUser.setLastName(user.getLastName());
            tmpUser.setStatus(String.valueOf(user.getStatus().name()));

            for(Role role : user.getRoles()) {
                String roleName = role.getName();
                tmpUser.setRole(roleName);
                break;
            }

            usersDTO.add(tmpUser);
        }

        return usersDTO;
    }


    @Override
    public User getByEmail(String email) {
        // User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email " + email));
        // return user;
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email " + email));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found id " + id));
    }

    @Override
    public UserDTO findByIdRest(Long id) {
        User user = userRepository.getById(id);

        UserDTO userDTO = convertorToDTOOneUser(user);
        return userDTO;
    }

    public UserDTO convertorToDTOOneUser(User user) {
        UserDTO tmpUserDTO = new UserDTO();
        tmpUserDTO.setId(user.getId());
        tmpUserDTO.setEmail(user.getEmail());
        tmpUserDTO.setPassword(user.getPassword());
        tmpUserDTO.setFirstName(user.getFirstName());
        tmpUserDTO.setLastName(user.getLastName());
        tmpUserDTO.setStatus(String.valueOf(user.getStatus().name()));

        for(Role role : user.getRoles()) {
            String roleName = role.getName();
            tmpUserDTO.setRole(roleName);
            break;
        }
        return tmpUserDTO;
    }

    @Override
    public User saveRest(UserDTO user) {
        validateUserDTO(user);

        User tmpUser = new User();
        tmpUser.setEmail(user.getEmail());
        tmpUser.setFirstName(user.getFirstName());
        tmpUser.setLastName(user.getLastName());
        tmpUser.setStatus(Status.valueOf(user.getStatus()));

        String roleName = user.getRole();
        Role receivedRole = roleService.findByName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(receivedRole);
        tmpUser.setRoles(roles);
        return userRepository.save(tmpUser);
    }

    @Override
    public User editRest(UserDTO user) {
        // Сознательно не делал валидацию (валидация как пример указана в saveRest(UserDTO user) выше
        User tmpUser = getById(user.getId()); // всё верно - получили нашего юзеза и что-то меняем
        tmpUser.setEmail(user.getEmail());
        tmpUser.setFirstName(user.getFirstName());
        tmpUser.setLastName(user.getLastName());
        tmpUser.setStatus(Status.valueOf(user.getStatus()));

        String roleName = user.getRole();
        Role receivedRole = roleService.findByName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(receivedRole);
        tmpUser.setRoles(roles);
        return userRepository.save(tmpUser);
    }


    // ************************ Controller`s Methods:

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) { /////////////////// DEPRECATED METHOD ---> see METHOD createUserWithRole()
        User tmpUser = getById(user.getId());
        tmpUser.setEmail(user.getEmail());
        tmpUser.setPassword(user.getPassword());
        tmpUser.setFirstName(user.getFirstName());
        tmpUser.setLastName(user.getLastName());
        tmpUser.setStatus(user.getStatus());
        // доделать CREATE
        tmpUser.setRoles(user.getRoles());
        return userRepository.save(tmpUser);
    }
    @Override
    public User update(UserDTO user) {
        User tmpUser = getById(user.getId()); // всё верно - получили нашего юзеза и что-то меняем
        tmpUser.setEmail(user.getEmail());
        tmpUser.setFirstName(user.getFirstName());
        tmpUser.setLastName(user.getLastName());
        tmpUser.setStatus(Status.valueOf(user.getStatus()));
        // Set<Role> roles = new HashSet<>();

        String roleName = user.getRole();
        Role receivedRole = roleService.findByName(roleName);

        Set<Role> roles = new HashSet<>();
        roles.add(receivedRole);

        //tmpUser.setRoles(Collections.singleton(receivedRole)); // - не работает
        tmpUser.setRoles(Stream.of(receivedRole).collect(Collectors.toSet()));
        //tmpUser.setRoles(roles); // - альтернатива верхней строчке 148 + 143 + 144
        //tmpUser.setRoles(Set.of(receivedRole)); // - не работает

        return userRepository.save(tmpUser);
    }

    //@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    @Override
    public User createUserWithRole(UserDTO user) {
        // В сервисе должна быть ДТО и маперы с пересприсваиванием полей. - создать доп метод - передает ДТО - получаем ЖПА юзера для записи в БД
//Long roleId, String status
        //User tmpUser = getById(user.getId());
        User tmpUser = new User();
        // CALL method of VALIDATION obj UserDTO user
        validateUserDTO(user); //

        tmpUser.setEmail(user.getEmail());

        // Шифруем пароль
        // Через поле
        // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // tmpUser.setPassword(user.getPassword());

        // Через БИН ------------ ПРАВИЛЬНО
        tmpUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //tmpUser.setPassword(bCryptPasswordEncoder.encode(tmpUser.getPassword()));

        tmpUser.setFirstName(user.getFirstName());
        tmpUser.setLastName(user.getLastName());

        // Устнавливаем статус
        //tmpUser.setStatus(user.getStatus());
//        if (status.equals("ACTIVE")) {
//            Status statusAc = Status.ACTIVE;
//            user.setStatus(statusAc);
//        } else {
//            Status statusBa = Status.BANNED;
//            user.setStatus(statusBa);
//        }
        tmpUser.setStatus(Status.valueOf(user.getStatus()));

        // доделать CREATE

        // Устанавливаем роль
        // Ищем роль по соответствию навзания - через присок ролей из БД и используя стрим с фильтром
//        String tmp_role_name = "USER";
//        List<Role> role_list = roleRepository.findAll();
//        role_list.stream().filter(r -> r.getName().equals(tmp_role_name));
        // Ищем роль по соответствию в roleId - через присок ролей из БД и используя стрим с фильтром
//        List<Role> role_list_id = roleRepository.findAll();
//        role_list_id.stream().filter(r -> r.getId() == roleId);

        // Рабочий код по установке Роли по roleId
//        Role tmpRole = roleRepository.findById(roleId).orElse(null);
//        Set<Role> roles = new HashSet<>();
//        roles.add(tmpRole);
//        tmpUser.setRoles(roles);

        //tmpUser.setRoles(user.setRoles(roles));
        Set<Role> roles = new HashSet<>();
        // Role tmpRole = new Role();

        String roleName = user.getRole();

        //tmpRole.setName(oneRole);
        //roles.add(tmpRole);
        //tmpUser.setRoles(roles);

        // Add role with Unique Key in DB
//        List<Role> role_list_id = roleService.findAll(); // List<Role> role_list_id = roleRepository.findAll();
//        role_list_id.stream().filter(r -> r.getName() == roleName);
//        Role tmpRole = role_list_id.get(0);
//        roles.add(tmpRole);
//        tmpUser.setRoles(roles);
        Role receivedRole = roleService.findByName(roleName);
        tmpUser.setRoles(Collections.singleton(receivedRole));

//        Role role1 = new Role();
//        Role role2 = new Role();
//        Role role3 = new Role();
//        List<Role> list1 = Arrays.asList(role1, role3, role2);/////////////
//
//        List<Role> collect = Stream.of(role1, role3, role2).collect(Collectors.toList()); /////////////////


        // через == Стринги не сравнивают
        // List<Role> roleName = roleRepository.findAll().stream().filter(r -> r.getName() == oneRole).collect(Collectors.toList());
        //List<Role> roleName = roleRepository.findAll().stream().filter(r -> r.getName().equals(oneRole)).collect(Collectors.toList());

        //Optional<Role> r = roleRepository.findByName(user.getRole()); // --- у Optional мы берем значения через .get()
        //Role needRole = roleRepository.findByName(user.getRole()).get(); // --- ПОЛУЧИЛОСЬ

        return userRepository.save(tmpUser);
    }


    public void validateUserDTO(UserDTO userDTO) {
//        if(userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) throw new NotValidDataException();
//        if(userDTO.getFirstName() == null || userDTO.getFirstName().isEmpty()) throw new NotValidDataException();
//        if(userDTO.getLastName() == null || userDTO.getLastName().isEmpty()) throw new NotValidDataException();
//        if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) throw new NotValidDataException();

        if(!StringUtils.hasText(userDTO.getEmail()) && !StringUtils.hasText(userDTO.getFirstName()) &&
                !StringUtils.hasText(userDTO.getLastName()) &&
                !StringUtils.hasText(userDTO.getPassword()) &&
                !StringUtils.hasText(userDTO.getPassword())) {
            throw new NotValidDataException();
        }

        // Создатть метод Боол который проверяет статус - и запихнуть в ИФ


//        Status status = (Status.valueOf(userDTO.getStatus()));
//        List<String> statuses = Status.getValuesAsString();
//
//        if(userDTO.getStatus() == null || userDTO.getStatus().isEmpty() || !(statuses.contains(status))) throw new NotValidDataException();

        //String roleName = userDTO.getRole();
        //Boolean checkRoleConsist = roleRepository.exists(userDTO.getRole());
        //Boolean checkRoleConsist_1 = roleRepository.anyMatch(userDTO.getRole());

        // Role checkRoleConsist= roleRepository.findByName(userDTO.getRole()).orElseThrow(() -> new NotValidDataException()); // Пустая лямбда

        //Role checkRoleConsist= roleService.findByName(userDTO.getRole());
        // if(userDTO.getRole() == null || userDTO.getRole().isEmpty() || checkRoleConsist == null) throw new NotValidDataException();


    }

    @Override
    public User deleteById(Long id) {
        userRepository.deleteById(id);
        return null;

    }
}

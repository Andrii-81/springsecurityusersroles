package com.example.springsecurityusersroles.service.impl;

import com.example.springsecurityusersroles.DTO.RoleDTO;
import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.Exception.NotValidDataException;
import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.Status;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.repository.RoleRepository;
import com.example.springsecurityusersroles.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }




    @Override
    public RoleDTO findByIdRest(Long id) {
        Role role = roleRepository.findById(id).orElse(null);

        RoleDTO roleDTO = convertorToDTOOneRole(role);
        return roleDTO;
    }

    public RoleDTO convertorToDTOOneRole(Role role) {
        RoleDTO tmpRoleDTO = new RoleDTO();
        tmpRoleDTO.setId(role.getId());
        tmpRoleDTO.setName(role.getName());
        return tmpRoleDTO;
    }

    @Override
    public Role saveRoleRest(RoleDTO roleDTO) {
        validateRoleDTO(roleDTO);
        Role tmpRole = new Role();
        tmpRole.setName(roleDTO.getName());
        return roleRepository.save(tmpRole);
    }

    public void validateRoleDTO(RoleDTO roleDTO) {
        if (!StringUtils.hasText(roleDTO.getName())) {
            throw new NotValidDataException();
        }
    }

    public Role editRoleRest(RoleDTO roleDTO) {
        Role role = findById(roleDTO.getId());
        validateRoleDTO(roleDTO);
        role.setName(roleDTO.getName());
        return roleRepository.save(role);
    }

    @Override
    public List<RoleDTO> findAllRolesRest() {
        List<Role> roles = roleRepository.findAll();

        List<RoleDTO> rolesDTO = convertorToListDTO(roles);
        return rolesDTO;
    }

    public List<RoleDTO> convertorToListDTO(List<Role> roles) {
        List<RoleDTO> rolesDTO = new ArrayList<>();
        for(Role role : roles) {
            RoleDTO tmpRole = new RoleDTO();
            tmpRole.setId(role.getId());
            tmpRole.setName(role.getName());

            rolesDTO.add(tmpRole);
        }
        return rolesDTO;
    }





    // ************************ Controller`s Methods:

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        //Role tmpRole = roleRepository.getReferenceById(id);
        Role tmpRole = roleRepository.findById(id).orElse(null);
        return tmpRole;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }


    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new NotValidDataException());
        // Change to my Custom Exception (RleEx)
    }


    @Override
    public Role editeRole(String name, Long id) {
        Role tmpRole = findById(id);
        tmpRole.setName(name);
        return roleRepository.save(tmpRole);

    }

    @Override
    public Role deleteById(Long id) {
        // удалит ссылки с users_roles затем с roles -
        // У меня каскадное связывание - если удалить роль, но строка с таблицы users_roles сама удалится
        //return null;
        roleRepository.deleteById(id);
        return null;
    }
}

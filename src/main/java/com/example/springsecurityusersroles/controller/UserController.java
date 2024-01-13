package com.example.springsecurityusersroles.controller;

import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.Exception.NotValidDataException;
import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.Status;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.service.RoleService;
import com.example.springsecurityusersroles.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ModelAndView getAllUsers(){
        ModelAndView modelAndView = new ModelAndView("user-list");
        modelAndView.addObject("users", userService.findAll());
//        List<User> my_list = userService.findAll();
//        for(User user : my_list) {
//            System.out.println(user.getLastName() + user.getRoles().stream().collect(Collectors.toList()));
//        }
        return modelAndView;
    }


    // USER CREATING
    // TO DO -> ModelAndView
    @GetMapping("/user-create")
    public String createUserForm(User user) {
        return "user-create";
    }

    @PostMapping("/user-create")
    public String createUser(User user) {
        userService.create(user);
        return "redirect:/users";
    }


    // UPDATE USER
    @GetMapping("/user-update/{id}")
    public ModelAndView updateUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getById(id);
        UserDTO tmpUser = new UserDTO(user);
        ModelAndView modelAndView = new ModelAndView("/user-update");

        List<String> statuses = Status.getValuesAsString();
        List<String> roles = roleService.findAll().stream().map(role -> role.getName()).collect(Collectors.toList()); // get all only names of roles
        // List<String> roles_1 = roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()); // get all only names of roles

        modelAndView.addObject("user", tmpUser);
        modelAndView.addObject("statuses", statuses);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @PostMapping("/user-update")
    // public ModelAndView updateUser(User user) {
    public RedirectView updateUser(@ModelAttribute UserDTO user) {
        final RedirectView redirectView  = new RedirectView("/users", true);

        try {
            userService.update(user);
            return redirectView;
        } catch (NotValidDataException e) {
            redirectView.setUrl("/user-update");
        }
        return redirectView;
    }




    // USER CREATING ---- WITH ROLE

//    @GetMapping("/user-create-with-role")
//    public String createUserRoleForm(User user) {
//        return "user-create";
//    }
//
//    @PostMapping("/user-create-with-role")
//    public String createUserRole(User user, Long roleId) {
//        userService.createUserWithRole(user, roleId);
//        return "redirect:/users";
//    }

    @GetMapping("/user-create-with-role")
    public ModelAndView createUserRoleForm(ModelMap model) {
        UserDTO user = new UserDTO();
//        List<String> statuses = new ArrayList<>();
//        statuses.add("ACTIVE");
//        statuses.add("BANNED");
        // List<Status> statuses = Arrays.stream(Status.values()).collect(Collectors.toList());

        List<String> statuses = Status.getValuesAsString();
        List<Role> roles = roleService.findAll();
        ModelAndView modelAndView = new ModelAndView("user-create-with-role");
        modelAndView.addObject("user", user);
        modelAndView.addObject("statuses", statuses);
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }
    @PostMapping("/user-create-with-role")
    public RedirectView createUserRole(@ModelAttribute UserDTO user) {
        final RedirectView redirectView  = new RedirectView("/users", true);
        try {
            userService.createUserWithRole(user);
            return redirectView;

        } catch (NotValidDataException e) {

            redirectView.setUrl("/user-create-with-role");
        }
        return redirectView;
    }


//    @PostMapping("/user-create-with-role")
//    public RedirectView createUserRole(@RequestParam long roleId, String status, User user) {
////        if ((user != null) && (roleId != 0) && (status != null || !status.equals("select status"))){
//
//            final RedirectView redirectView = new RedirectView("/users", true);
//            userService.createUserWithRole(user, roleId, status); // role - это id
//            return redirectView;

//        } else {
//            final RedirectView redirectView = new RedirectView("/user-create-with-role", true);
//            return redirectView;
//        }
        //ModelAndView modelAndView = new ModelAndView("/user-create-with-role");

//
//    }





    // DELETE USER
    @GetMapping("/user-delete/{id}")
    public ModelAndView deleteUserForm(@PathVariable("id") Long id, ModelMap model) {
        User user = userService.getById(id);
        UserDTO tmpUser = new UserDTO(user); /////// IMPORTANT  new UserDTO(user)
        ModelAndView modelAndView = new ModelAndView("/user-delete");
//        List<String> statuses = Status.getValuesAsString();
//        List<String> roles = roleService.findAll().stream().map(role -> role.getName()).collect(Collectors.toList()); // get all only names of roles
        // List<String> roles_1 = roleService.findAll().stream().map(Role::getName).collect(Collectors.toList()); // get all only names of roles
        modelAndView.addObject("user", tmpUser);
//        modelAndView.addObject("statuses", statuses);
//        modelAndView.addObject("roles", roles);
        return modelAndView;
    }
    @PostMapping("/user-delete")
    // public ModelAndView updateUser(User user) {
    public RedirectView deleteUser(@ModelAttribute UserDTO user) {
        final RedirectView redirectView  = new RedirectView("/users", true);
        try {
            userService.deleteById(user.getId());
            return redirectView;
        } catch (NotValidDataException e) {
            redirectView.setUrl("/user-delete");
        }
        return redirectView;
    }






}

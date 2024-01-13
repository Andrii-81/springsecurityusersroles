package com.example.springsecurityusersroles.controller;

import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String findAll(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "role-list";
    }



    // ROLE CREATING

    @GetMapping("/role-create")
    public String createRoleForm(Role role) {
        return "role-create";
    }

    @PostMapping("/role-create")
    public String createRole(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }



    // ROLE EDIT

    @GetMapping("/role-update/{id}")
    public String updateRoleForm(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role", role);
        return "role-update";
    }
    @PostMapping("/role-update")
    public String updateRole(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    // ROLE DELETE
    @GetMapping("/role-delete/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return "redirect:/roles";
    }


    //    @GetMapping("roles/{id}")
//    //@PreAuthorize("hasAuthority('ADMIN')")
//    public Role getById(@PathVariable Long id) {
//        Role role = new Role();
//        role = roleService.findById(id);
//        return role;
//    }

}

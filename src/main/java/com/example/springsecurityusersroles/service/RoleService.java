package com.example.springsecurityusersroles.service;

import com.example.springsecurityusersroles.DTO.RoleDTO;
import com.example.springsecurityusersroles.model.Role;

import java.util.List;

public interface RoleService {

    public RoleDTO findByIdRest(Long id);
    public Role saveRoleRest(RoleDTO roleDTO);
    public Role editRoleRest(RoleDTO roleDTO);
    public List<RoleDTO> findAllRolesRest();




    List<Role> findAll();
    Role save(Role role);

    Role findByName(String name);

    Role findById(Long id);

    Role editeRole(String name, Long id);

    Role deleteById(Long id);
}

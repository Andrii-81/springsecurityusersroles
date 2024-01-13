package com.example.springsecurityusersroles.rest;

import com.example.springsecurityusersroles.DTO.RoleDTO;
import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.service.RoleService;
import com.example.springsecurityusersroles.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles/")
public class RoleRestController {

    private final RoleService roleService;
    private final UserService userService;


    public RoleRestController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @RequestMapping(value ="{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoleDTO> getRole(@PathVariable("id") Long roleId) {
        if(roleId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        RoleDTO role = this.roleService.findByIdRest(roleId);
        if(role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoleDTO> saveRole(@RequestBody RoleDTO roleDTO) {
        HttpHeaders headers = new HttpHeaders(); // Хэдэры, пишутся в заголовке нашего запроса или ответа
        if(roleDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roleService.saveRoleRest(roleDTO);
        return new ResponseEntity<>(roleDTO, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoleDTO> editRole(@RequestBody RoleDTO roleDTO, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();
        if(roleDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        roleService.editRoleRest(roleDTO);
        return new ResponseEntity<>(roleDTO, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable("id") Long roleId) {
        Role role = roleService.findById(roleId);

        if(role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.roleService.deleteById(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> roles = roleService.findAllRolesRest();

        if(roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}

package com.example.springsecurityusersroles.rest;

import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.service.RoleService;
import com.example.springsecurityusersroles.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> users = userService.findAllRest();

        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long userId) {
        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO user = this.userService.findByIdRest(userId);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {
        HttpHeaders headers = new HttpHeaders();

        if(userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.saveRest(userDTO);
        return new ResponseEntity<>(userDTO, headers, HttpStatus.CREATED);
    }



    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO, UriComponentsBuilder builder) {
        HttpHeaders header = new HttpHeaders();

        if(userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.userService.editRest(userDTO);
        return new ResponseEntity<>(userDTO, header, HttpStatus.OK);

    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long userId) {
        User user = this.userService.getById(userId);

        if(user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.userService.deleteById(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

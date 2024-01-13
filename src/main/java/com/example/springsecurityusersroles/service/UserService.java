package com.example.springsecurityusersroles.service;

import com.example.springsecurityusersroles.DTO.UserDTO;
import com.example.springsecurityusersroles.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    public List<UserDTO> findAllRest();

    User getByEmail(String email);

    User getById(Long id);

    public UserDTO findByIdRest(Long id);

    User update(UserDTO user);

    public User saveRest(UserDTO user);
    public User editRest(UserDTO user);

    User create(User user);

    User createUserWithRole(UserDTO user);

    User deleteById(Long id);
}

package com.example.springsecurityusersroles.repository;

import com.example.springsecurityusersroles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);



    // Это методы, по которым мы получаем Юзера из БД
//    Optional<User> findById (Long id);
//
//    List<User> findAll();

}

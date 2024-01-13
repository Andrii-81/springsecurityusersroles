package com.example.springsecurityusersroles.security;

import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.repository.RoleRepository;
import com.example.springsecurityusersroles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Такого Юзера нет!! Рафик ни в чём не виноват!"));

        return SecurityUser.fromUser(user);
    }

    // dobavil
//    public Optional<User> findByUserName(String email) {
//        return userRepository.findByEmail(email);
//    }
}
package com.example.springsecurityusersroles.security;

import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.Status;
import com.example.springsecurityusersroles.model.User;
import com.example.springsecurityusersroles.repository.RoleRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    //private final Role role;
    //@Autowired
    //private RoleRepository roleRepository;

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) { //, Role role) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;

        //this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    //@Override
//    public Role getRole() {
//        // return role;
//    }

    public static UserDetails fromUser(User user) {
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                user.getStatus().equals(Status.ACTIVE),
//                user.getStatus().equals(Status.ACTIVE),
//                user.getStatus().equals(Status.ACTIVE),
//                user.getStatus().equals(Status.ACTIVE),
//                user.getRole().getAuthorities()
//        );

//        return org.springframework.security.core.userdetails.User.builder()
//                .authorities(user.getRole().name())
//                //.roles(user.getRole().name())
//                .password(user.getPassword())
//                .username(user.getEmail())
//                .build();


        // ХИМИЧУ
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),
                user.getStatus().equals(Status.ACTIVE),

                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())

//                for (Role role : user.getRoles())  {
//                    if (role.equals())
//        }
        );

    }

}

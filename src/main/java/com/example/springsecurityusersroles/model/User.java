package com.example.springsecurityusersroles.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

//    @Enumerated(value = EnumType.STRING)
//    @Column(name = "role")
//    private Role role ;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status ;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id",  referencedColumnName = "id")}
    )
    private Set<Role> roles = new HashSet<>();
    //private Collection<Role> roles;

    //@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)

    public User() {
    }
    public User(String email, String password, String firstName, String lastName, Status status, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.roles = roles;
    }



    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

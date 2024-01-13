package com.example.springsecurityusersroles.DTO;

import com.example.springsecurityusersroles.model.Status;
import com.example.springsecurityusersroles.model.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String role ;

    private String status ;

    public UserDTO(User userJpa){
        this.id = userJpa.getId();
        this.email = userJpa.getEmail();;
        this.lastName = userJpa.getLastName();
        this.password = userJpa.getPassword();
        this.firstName = userJpa.getFirstName();
        this.role = userJpa.getRoles().iterator().next().getName();
        this.status = userJpa.getStatus().name();
    }

}

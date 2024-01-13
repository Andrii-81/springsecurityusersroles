package com.example.springsecurityusersroles.DTO;

import com.example.springsecurityusersroles.model.Role;
import com.example.springsecurityusersroles.model.User;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;

    private String name;


    public RoleDTO(Role roleJpa){
        this.id = roleJpa.getId();
        this.name = roleJpa.getName();;

    }

}




package com.killiangodet.recette.user.model.response;

import com.killiangodet.recette.role.model.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"roleId", "genderId", "firstname", "lastname", "username", "email", "dateOfBirth", "membershipId"})
public class UserAdminResponseDTO {

    private Integer userId;
    private Integer roleId;
    private Integer genderId;
    private String firstname;
    private String lastname;
    private String pseudo;
    private String username;
    private LocalDate dateOfBirth;
    private Integer membershipId;

    public UserAdminResponseDTO(Integer userId, Integer roleId, Integer genderId, String firstname, String lastname, String pseudo, String username, LocalDate dateOfBirth, Integer membershipId){
        this.userId = userId;
        this.roleId = roleId;
        this.genderId = genderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.membershipId = membershipId;
    }
}

package com.killiangodet.recette.user.model.response;

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

    private Integer id;
    private Integer roleId;
    private Integer genderId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private Integer membershipId;

    public UserAdminResponseDTO(Integer id, Integer roleId, Integer genderId, String firstname, String lastname, String username, String email, LocalDate dateOfBirth, Integer membershipId){
        this.id = id;
        this.roleId = roleId;
        this.genderId = genderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.membershipId = membershipId;
    }
}

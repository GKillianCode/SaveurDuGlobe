package com.killiangodet.recette.user.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private Integer genderId;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private LocalDate dateOfBirth;
    private Integer membershipId;

    public UserResponseDTO(Integer genderId, String firstname, String lastname, String username, String email, LocalDate dateOfBirth, Integer membershipId){
        this.genderId = genderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.membershipId = membershipId;
    }

}

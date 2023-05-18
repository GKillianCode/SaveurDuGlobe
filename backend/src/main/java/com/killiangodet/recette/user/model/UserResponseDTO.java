package com.killiangodet.recette.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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

    public UserResponseDTO(Integer genderId, String firstname, String lastname, String username, String email, LocalDate dateOfBirth){
        this.genderId = genderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

}

package com.killiangodet.recette.user.model;

import com.killiangodet.recette.gender.GenderService;
import com.killiangodet.recette.membership.MembershipService;
import com.killiangodet.recette.role.RoleService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"firstname", "lastname", "username", "email", "dateOfBirth", "genderId"})
public class UserDTO {
    @Size(min = 2, max = 50, message = "Fistname not valid")
    private String firstname;

    @Size(min = 2, max = 50, message = "Fistname not valid")
    private String lastname;

    @Size(min = 2, max = 50, message = "Fistname not valid")
    private String username;

    @Email(message = "Email not valid")
    private String email;

    @Size(min = 8, max = 50, message = "Password not valid")
    private String password;

    private LocalDate dateOfBirth;

    @Min(value = 1, message = "GenderId not valid")
    private Integer genderId;

    public UserDTO(String firstname, String lastname, String username, String email, String password, LocalDate dateOfBirth, Integer genderId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.genderId = genderId;
    }

    public User toEntity() {
        User user = new User();
        user.setFirstname(this.getFirstname());
        user.setLastname(this.getLastname());
        user.setUsername(this.getUsername());
        user.setEmail(this.getEmail());
        user.setPassword(this.getPassword());
        user.setDateOfBirth(this.getDateOfBirth());
        user.setRoleId(3);
        user.setGenderId(this.getGenderId());
        user.setMembershipId(2);

        return user;
    }
}

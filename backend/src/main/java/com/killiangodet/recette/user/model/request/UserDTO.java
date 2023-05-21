package com.killiangodet.recette.user.model.request;

import com.killiangodet.recette.gender.GenderService;
import com.killiangodet.recette.membership.MembershipService;
import com.killiangodet.recette.role.RoleService;
import com.killiangodet.recette.role.model.Role;
import com.killiangodet.recette.role.repository.RoleRepository;
import com.killiangodet.recette.user.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"firstname", "lastname", "username", "pseudo", "dateOfBirth", "genderId"})
public class UserDTO {
    @Size(min = 2, max = 50, message = "Fistname not valid")
    private String firstname;

    @Size(min = 2, max = 50, message = "Fistname not valid")
    private String lastname;

    @Size(min = 2, max = 50, message = "Pseudo not valid")
    private String pseudo;

    @Email(message = "Email not valid")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,50}$", message = "Password not valid")
    private String password;

    private LocalDate dateOfBirth;

    @Min(value = 1, message = "GenderId not valid")
    private Integer genderId;

    public UserDTO(String firstname, String lastname, String pseudo, String username, String password, LocalDate dateOfBirth, Integer genderId){
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.username = username;
        this.password = password;
        this.genderId = genderId;
        this.dateOfBirth = dateOfBirth;
    }
}

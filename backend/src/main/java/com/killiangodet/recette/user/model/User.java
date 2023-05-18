package com.killiangodet.recette.user.model;

import com.killiangodet.recette.gender.model.Gender;
import com.killiangodet.recette.membership.model.Membership;
import com.killiangodet.recette.role.model.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"genderId", "firstname", "lastname", "username", "dateOfBirth", "email"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private int id;

    @Column(name = "usr_gender_id")
    private Integer genderId;

    @Column(name = "usr_firstname")
    private String firstname;

    @Column(name = "usr_lastname")
    private String lastname;

    @Column(name = "usr_username")
    private String username;

    @Column(name = "usr_date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_hash_password")
    private String password;

    @Column(name = "usr_membership_id")
    private Integer membershipId;

    @Column(name = "usr_role_id")
    private Integer roleId;
}

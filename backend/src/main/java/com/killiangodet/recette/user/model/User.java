package com.killiangodet.recette.user.model;

import com.killiangodet.recette.gender.model.Gender;
import com.killiangodet.recette.role.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "gender_id", insertable = false, updatable = false)
    private Gender gender;

    @Column(name = "usr_firstName")
    private String firstname;

    @Column(name = "usr_lastName")
    private String lastname;

    @Column(name = "usr_username")
    private String username;

    @Column(name = "usr_dateOfBirth")
    private Calendar dateOfBirth;

    @Column(name = "usr_email")
    private String email;

    @Column(name = "usr_hashPassword")
    private String password;

    @Column(name = "usr_membershipId")
    private Integer membershipId;

    @ManyToOne
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;
}

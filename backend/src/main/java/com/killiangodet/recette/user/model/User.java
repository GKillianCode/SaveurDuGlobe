package com.killiangodet.recette.user.model;

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

    @Column(name = "usr_genderId")
    private Integer genderId;

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

    @Column(name = "usr_roleId")
    private Integer roleId;
}

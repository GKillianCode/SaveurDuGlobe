package com.killiangodet.recette.user.model;

import com.killiangodet.recette.role.model.Role;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"genderId", "firstname", "lastname", "pseudo", "dateOfBirth", "username"})
public class User implements UserDetails {

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
    private String pseudo;

    @Column(name = "usr_email")
    private String username;

    @Column(name = "usr_date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "usr_hash_password")
    private String password;

    @Column(name = "usr_membership_id")
    private Integer membershipId;

    @ManyToOne
    @JoinColumn(name = "usr_role_id")
    private Role role;

    public User(Integer genderId, String firstname, String lastname, String pseudo, LocalDate dateOfBirth, String username, String password, Integer membershipId, Role role){
        this.genderId = genderId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
        this.membershipId = membershipId;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.killiangodet.recette.gender.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gender")
@Getter
@NoArgsConstructor
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gender_id")
    private Integer id;

    @Column(name = "gender_name")
    private String name;
}

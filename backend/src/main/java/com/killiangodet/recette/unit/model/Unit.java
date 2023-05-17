package com.killiangodet.recette.unit.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit")
@Getter
@NoArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer id;

    @Column(name = "unit_name")
    private String name;

    @Column(name = "unit_shortName")
    private String shortName;
}

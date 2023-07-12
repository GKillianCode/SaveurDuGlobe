package com.killiangodet.recette.unit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "shortName"})
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Integer id;

    @Column(name = "unit_name")
    private String name;

    @Column(name = "unit_short_name")
    private String shortName;
}

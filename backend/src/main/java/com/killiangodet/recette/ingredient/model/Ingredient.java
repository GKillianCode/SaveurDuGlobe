package com.killiangodet.recette.ingredient.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient")
@Getter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "igt_id")
    private Integer id;

    @Column(name = "igt_name")
    private String name;

    @Column(name = "igt_quantity")
    private Integer quantity;

    @Column(name = "igt_recipeId")
    private Integer recipeId;

    @Column(name = "igt_unitId")
    private Integer unitId;
}

package com.killiangodet.recette.ingredient.model;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.unit.model.Unit;
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

    @ManyToOne
    @JoinColumn(name = "rcp_id", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    private Unit unit;
}

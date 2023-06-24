package com.killiangodet.recette.ingredient.model;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.unit.model.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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

    @Size(min = 2, max = 255, message = "Ingredient name not valid")
    @Column(name = "igt_name")
    private String name;


    @Column(name = "igt_quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "igt_recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "igt_unit_id")
    private Unit unit;

    public Ingredient(String name, Integer quantity, Unit unit, Recipe recipe) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.recipe = recipe;
    }
}

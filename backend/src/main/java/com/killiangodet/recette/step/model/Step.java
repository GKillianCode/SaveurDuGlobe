package com.killiangodet.recette.step.model;

import com.killiangodet.recette.recipe.model.Recipe;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "step")
@Getter
@NoArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Integer id;

    @Column(name = "step_order_id")
    @Min(value = 1, message = "Step orderId not valid")
    private Integer orderId;

    @Column(name = "step_description")
    @Size(min = 20, max = 500, message = "Step description not valid")
    private String description;

    @ManyToOne
    @JoinColumn(name = "step_recipe_id")
    private Recipe recipe;

    public Step(Integer orderId, String description, Recipe recipe){
        this.orderId = orderId;
        this.description = description;
        this.recipe = recipe;
    }
}

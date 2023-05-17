package com.killiangodet.recette.step.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "step")
@Getter
@NoArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Integer id;

    @Column(name = "step_orderId")
    private Integer orderId;

    @Column(name = "step_description")
    private String description;

    @Column(name = "step_recipeId")
    private Integer recipeId;
}

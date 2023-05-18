package com.killiangodet.recette.step.model;

import com.killiangodet.recette.recipe.model.Recipe;
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

    @ManyToOne
    @JoinColumn(name = "rcp_id", insertable = false, updatable = false)
    private Recipe recipe;
}

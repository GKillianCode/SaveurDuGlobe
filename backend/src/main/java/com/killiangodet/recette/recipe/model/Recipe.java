package com.killiangodet.recette.recipe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Getter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_id")
    private Integer id;

    @Column(name = "rcp_title")
    private String title;

    @Column(name = "rcp_description")
    private String description;

    @Column(name = "rcp_preparationTime")
    private Integer preparationTime;

    @Column(name = "rcp_cookTime")
    private Integer cookTime;

    @Column(name = "rcp_difficulty")
    private Integer difficulty;

    @Column(name = "userId")
    private Integer userId;
}

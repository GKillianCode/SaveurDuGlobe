package com.killiangodet.recette.recipe.model;

import com.killiangodet.recette.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_id")
    private Integer id;

    @Column(name = "rcp_title")
    private String title;

    @Column(name = "rcp_description")
    private String description;

    @Column(name = "rcp_preparation_time")
    private Integer preparationTime;

    @Column(name = "rcp_cook_time")
    private Integer cookTime;

    @Column(name = "rcp_difficulty")
    private Integer difficulty;

    @Column(name = "rcp_nb_person")
    private Integer nbPerson;

    @ManyToOne
    @JoinColumn(name = "rcp_user_id", insertable = false, updatable = false)
    private User user;
}

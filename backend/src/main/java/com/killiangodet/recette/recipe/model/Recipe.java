package com.killiangodet.recette.recipe.model;

import com.killiangodet.recette.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"title", "description", "preparationTime", "cookTime", "difficulty", "nbPerson", "user"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rcp_id")
    private Integer id;

    @Size(min = 2, max = 255, message = "Title not valid")
    @Column(name = "rcp_title")
    private String title;

    @Size(min = 2, max = 400, message = "Description not valid")
    @Column(name = "rcp_description")
    private String description;

    @Min(value = 0, message = "PreparationTime not valid")
    @Column(name = "rcp_preparation_time")
    private Integer preparationTime;

    @Min(value = 0, message = "CookTime not valid")
    @Column(name = "rcp_cook_time")
    private Integer cookTime;

    @Min(value = 1, message = "Difficulty not valid")
    @Max(value = 3, message = "Difficulty not valid")
    @Column(name = "rcp_difficulty")
    private Integer difficulty;

    @Min(value = 1)
    @Min(value = 1, message = "NbPerson not valid")
    @Column(name = "rcp_nb_person")
    private Integer nbPerson;

    @ManyToOne
    @JoinColumn(name = "rcp_user_id")
    private User user;

    public Recipe(String title, String description, Integer preparationTime, Integer cookTime, Integer difficulty, Integer nbPerson, User user) {
        this.title = title;
        this.description = description;
        this.preparationTime = preparationTime;
        this.cookTime = cookTime;
        this.difficulty = difficulty;
        this.nbPerson = nbPerson;
        this.user = user;
    }
}

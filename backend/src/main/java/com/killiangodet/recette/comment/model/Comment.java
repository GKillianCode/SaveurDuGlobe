package com.killiangodet.recette.comment.model;

import com.killiangodet.recette.rating.model.Rating;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"description", "user", "recipe", "rating"})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmt_id")
    private Integer id;

    @Column(name = "cmt_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cmt_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cmt_recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "cmt_rating_id")
    private Rating rating;

    public Comment(String description, User user, Recipe recipe, Rating rating){
        this.description = description;
        this.user = user;
        this.recipe = recipe;
        this.rating = rating;
    }
}

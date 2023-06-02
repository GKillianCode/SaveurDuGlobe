package com.killiangodet.recette.favoriteRecipe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode(of = {"recipeId", "userId"})
public class FavoriteRecipeId implements Serializable {
    @Column(name = "fv_recipe_id")
    private Long recipeId;

    @Column(name = "fv_user_id")
    private Long userId;
}



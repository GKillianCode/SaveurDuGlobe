package com.killiangodet.recette.favoriteRecipe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "favoriteRecipe")
@Getter
@NoArgsConstructor
public class FavoriteRecipe {

    @EmbeddedId
    private FavoriteRecipeId id;

    @Embeddable
    @Getter
    @NoArgsConstructor
    public static class FavoriteRecipeId implements Serializable {
        @Column(name = "fv_recipeId")
        private Integer recipeId;

        @Column(name = "fv_userId")
        private Integer userId;
    }
}

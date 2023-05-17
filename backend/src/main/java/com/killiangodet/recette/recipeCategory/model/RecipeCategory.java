package com.killiangodet.recette.recipeCategory.model;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "recipeCategory")
@Getter
@NoArgsConstructor
public class RecipeCategory {

        @EmbeddedId
        private RecipeCategory.RecipeCategoryId id;

        @Embeddable
        @Getter
        @NoArgsConstructor
        public static class RecipeCategoryId implements Serializable {
                @Column(name = "rc_categoryId")
                private Integer categoryId;

                @Column(name = "rc_recipeId")
                private Integer recipeId;
        }
}

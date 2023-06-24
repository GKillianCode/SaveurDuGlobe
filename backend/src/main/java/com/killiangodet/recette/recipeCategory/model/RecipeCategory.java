package com.killiangodet.recette.recipeCategory.model;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "recipecategory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCategory {

        @EmbeddedId
        private RecipeCategory.RecipeCategoryId id;

        @Embeddable
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RecipeCategoryId implements Serializable {
                @Column(name = "rc_category_id")
                private Integer categoryId;

                @Column(name = "rc_recipe_id")
                private Integer recipeId;
        }
}

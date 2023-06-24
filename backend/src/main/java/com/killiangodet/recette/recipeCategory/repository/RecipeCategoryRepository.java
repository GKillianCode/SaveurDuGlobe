package com.killiangodet.recette.recipeCategory.repository;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, RecipeCategory.RecipeCategoryId> {

    @Query("SELECT rc FROM RecipeCategory rc WHERE rc.id.recipeId = :recipeId")
    List<RecipeCategory> findAllByRecipeId(@Param("recipeId") Integer recipeId);
}

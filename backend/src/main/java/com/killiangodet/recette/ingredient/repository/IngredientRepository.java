package com.killiangodet.recette.ingredient.repository;

import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findAllByRecipe(Recipe recipe);
}

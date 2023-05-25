package com.killiangodet.recette.recipe.repository;

import com.killiangodet.recette.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}

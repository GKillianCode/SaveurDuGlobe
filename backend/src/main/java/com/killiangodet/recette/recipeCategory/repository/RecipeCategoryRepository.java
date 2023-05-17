package com.killiangodet.recette.recipeCategory.repository;

import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
}

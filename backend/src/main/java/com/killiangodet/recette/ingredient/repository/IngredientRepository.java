package com.killiangodet.recette.ingredient.repository;

import com.killiangodet.recette.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}

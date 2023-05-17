package com.killiangodet.recette.favoriteRecipe.repository;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Integer> {
}

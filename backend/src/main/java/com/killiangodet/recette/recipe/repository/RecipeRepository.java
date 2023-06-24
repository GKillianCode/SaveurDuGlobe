package com.killiangodet.recette.recipe.repository;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findOneByUserAndTitle(User user, String title);
}

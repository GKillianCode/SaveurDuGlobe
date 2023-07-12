package com.killiangodet.recette.recipe.repository;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Optional<Recipe> findOneByUserAndTitle(User user, String title);
    Page<Recipe> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Optional<Recipe> findOneById(Integer recipeId);
}

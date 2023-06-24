package com.killiangodet.recette.step.repository;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.step.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StepRepository extends JpaRepository<Step, Integer> {
    List<Step> findAllByRecipe(Recipe recipe);

    List<Step> findAllByRecipeOrderByOrderId(Recipe recipe);
}

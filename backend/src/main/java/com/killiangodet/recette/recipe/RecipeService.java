package com.killiangodet.recette.recipe;

import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipe.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    public Recipe getRecipeById(Integer recipeId) {
        System.out.println("---- START GET RECIPE ----");
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        System.out.println("---- BDD OK ----");
        if(recipe.isPresent()){
            System.out.println("---- IS PRESENT OK ----");
            return recipe.get();
        }
        System.out.println("---- IS PRESENT KO ----");

        throw new EntityNotFoundException();
    }
}

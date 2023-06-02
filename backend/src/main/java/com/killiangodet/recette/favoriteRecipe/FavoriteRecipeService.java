package com.killiangodet.recette.favoriteRecipe;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipeId;
import com.killiangodet.recette.favoriteRecipe.repository.FavoriteRecipeRepository;
import com.killiangodet.recette.recipe.RecipeService;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteRecipeService {

    @Autowired
    FavoriteRecipeRepository favoriteRecipeRepository;

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    /**
     * Vérifie si une recette favorite (par utilisateur et par recette) existe
     *
     * @param userId
     * @param recipeId
     * @return true si la recette favorite existe sinon false
     */
    public boolean existsFavoriteRecipeByUserIdAndRecipeId(Integer userId, Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User user = userService.getUserById(userId);

        if(recipe == null || user == null){
            System.out.println("USER OR RECIPE FAILED");
            throw new EntityExistsException();
        }

        System.out.println("EXISTS START");
        long count = favoriteRecipeRepository.countFavoriteRecipesByRecipeIdAndUserId(userId, recipeId);

        System.out.println("COUNT OK");
        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Recherche une recette favorite par "userId" et "recipeId"
     *
     * @param userId
     * @param recipeId
     * @return true si la recette favorite existe sinon false
     */
    public FavoriteRecipe findOneFavoriteRecipeByUserIdAndRecipeId(Integer userId, Integer recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);
        User user = userService.getUserById(userId);

        if(recipe == null || user == null){
            System.out.println("USER OR RECIPE FAILED");
            throw new EntityExistsException();
        }

        System.out.println("EXISTS START");
        FavoriteRecipe fv = favoriteRecipeRepository.findFavoriteRecipesByRecipeIdAndUserId(userId, recipeId);

        System.out.println("NOT NULL START");
        if(fv == null){
            throw new EntityNotFoundException();
        }

        return fv;
    }


    /**
     * Enregistre la recette favorite en bdd
     *
     * @param fv
     */
    public void saveFavoriteRecipe(FavoriteRecipe fv){
        favoriteRecipeRepository.save(fv);
    }

    public void deleteComment(FavoriteRecipe favoriteRecipe) {
        favoriteRecipeRepository.delete(favoriteRecipe);
    }
}

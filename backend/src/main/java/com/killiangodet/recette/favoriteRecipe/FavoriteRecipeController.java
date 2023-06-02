package com.killiangodet.recette.favoriteRecipe;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipeId;
import com.killiangodet.recette.favoriteRecipe.model.request.FavoriteRecipeDTO;
import com.killiangodet.recette.recipe.RecipeService;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorite-recipe")
@Validated
@Tag(name = "FavoriteRecipe")
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@SecurityRequirement(name = "Authorization")
public class FavoriteRecipeController {

    @Autowired
    FavoriteRecipeService favoriteRecipeService;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    /**
     * Ajoute une recette favorite à un utilisateur
     *
     * @param authentication
     * @param favoriteRecipeDTO
     * @return "Recette ajoutée aux favoris" en cas de succès
     */
    @PostMapping("/add")
    public ResponseEntity<String> add(Authentication authentication, @RequestBody FavoriteRecipeDTO favoriteRecipeDTO) {
        User user = userService.getUserByUsername(authentication.getName());

        boolean existsFv = favoriteRecipeService.existsFavoriteRecipeByUserIdAndRecipeId(user.getId(), favoriteRecipeDTO.getRecipeId());

        if (existsFv) {
            throw new EntityExistsException();
        }
        Recipe recipe = recipeService.getRecipeById(favoriteRecipeDTO.getRecipeId());

        if(recipe == null){
            throw new EntityNotFoundException();
        }

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(
                new FavoriteRecipeId((long)recipe.getId(), (long)user.getId())
        );

        favoriteRecipeService.saveFavoriteRecipe(favoriteRecipe);
        return ResponseEntity.ok("Recette ajoutée aux favoris");
    }

    /**
     * Supprime une recette favorite d'un utilisateur
     *
     * @param authentication
     * @param favoriteRecipeDTO
     * @return "Recette supprimée des favoris" en cas de succès
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> remove(Authentication authentication, @RequestBody FavoriteRecipeDTO favoriteRecipeDTO){

        User user = userService.getUserByUsername(authentication.getName());

        boolean existsFv = favoriteRecipeService.existsFavoriteRecipeByUserIdAndRecipeId(user.getId(), favoriteRecipeDTO.getRecipeId());

        if (!existsFv) {
            throw new EntityNotFoundException();
        }
        Recipe recipe = recipeService.getRecipeById(favoriteRecipeDTO.getRecipeId());

        if(recipe == null){
            throw new EntityNotFoundException();
        }

        FavoriteRecipe favoriteRecipe = new FavoriteRecipe(
                new FavoriteRecipeId((long)recipe.getId(), (long)user.getId())
        );

        favoriteRecipeService.deleteComment(favoriteRecipe);

        return ResponseEntity.ok("Recette supprimée des favoris");
    }
}

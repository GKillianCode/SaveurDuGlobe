package com.killiangodet.recette.favoriteRecipe.repository;

import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipeId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRecipeRepository extends CrudRepository<FavoriteRecipe, FavoriteRecipeId> {

    /**
     * Compte le nombre de recettes favorites par utilisateur et par recette
     *
     * @param userId
     * @param recipeId
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM FavoriteRecipe WHERE fv_recipe_id = :recipeId AND fv_user_id = :userId", nativeQuery = true)
    long countFavoriteRecipesByRecipeIdAndUserId(@Param("userId") Integer userId, @Param("recipeId") Integer recipeId);

    /**
     * Recherche une recette favorite par userId et recipeId
     *
     * @param userId
     * @param recipeId
     * @return
     */
    @Query(value = "SELECT fv.* FROM FavoriteRecipe fv WHERE fv_recipe_id = :recipeId AND fv_user_id = :userId LIMIT 1", nativeQuery = true)
    FavoriteRecipe findFavoriteRecipesByRecipeIdAndUserId(@Param("userId") Integer userId, @Param("recipeId") Integer recipeId);
}



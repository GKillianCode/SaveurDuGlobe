package com.killiangodet.recette.image.repository;

import com.killiangodet.recette.image.model.Image;
import com.killiangodet.recette.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    void deleteByRecipe(Recipe recipe);

    Image findByRecipe(Recipe recipe);

    Image findOneByRecipe(Recipe recipe);
}

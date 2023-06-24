package com.killiangodet.recette.ingredient;

import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.ingredient.model.request.IngredientDTO;
import com.killiangodet.recette.ingredient.repository.IngredientRepository;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.unit.UnitService;
import com.killiangodet.recette.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    UserService userService;

    @Autowired
    UnitService unitService;

    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public void addAllIngredients(List<IngredientDTO> ingredientDTOS, Recipe recipe) throws Exception{
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientDTOS.forEach(
                ingredient -> ingredients.add(new Ingredient(
                                ingredient.getName(),
                                ingredient.getQuantity(),
                                unitService.getUnitById(ingredient.getUnitId()),
                                recipe
                        )
                ));
        ingredients.forEach(
                this::save
        );
    }

    public List<Ingredient> getAllByRecipe(Recipe recipe) {
        return ingredientRepository.findAllByRecipe(recipe);
    }

    public void removeAll(List<Ingredient> ingredients) {
        ingredientRepository.deleteAll(ingredients);
    }

    public void removeAllByRecipe(Recipe recipe){
        List<Ingredient> ingredients = getAllByRecipe(recipe);
        removeAll(ingredients);
    }
}

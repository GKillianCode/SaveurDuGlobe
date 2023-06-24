package com.killiangodet.recette.recipe;

import com.killiangodet.recette.category.CategoryService;
import com.killiangodet.recette.category.model.Category;
import com.killiangodet.recette.category.model.CategoryDTO;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.ingredient.model.response.ResponseIngredientDTO;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipe.model.response.ResponseFullRecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseRecipeDTO;
import com.killiangodet.recette.recipe.repository.RecipeRepository;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.step.model.Step;
import com.killiangodet.recette.step.model.StepDTO;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    StepService stepService;

    @Autowired
    RecipeCategoryService recipeCategoryService;

    @Autowired
    CategoryService categoryService;

    public Recipe getRecipeById(Integer recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isPresent()){
            return recipe.get();
        }

        throw new EntityNotFoundException();
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getOneRecipeByUserAndTitle(User user, String title) {
        Optional<Recipe> recipe = recipeRepository.findOneByUserAndTitle(user, title);
        return recipe.orElse(null);
    }

    public void checkIfRecipeExists(User user, String title) {
        Recipe recipe = getOneRecipeByUserAndTitle(user, title);

        if(recipe != null){
            throw new EntityExistsException("Entity already exists");
        }
    }

    public void remove(Recipe recipe) {
        recipeRepository.deleteById(recipe.getId());
    }

    public ResponseFullRecipeDTO getFullRecipeDTO(Recipe recipe, String imageBase64){
        ResponseRecipeDTO responseRecipeDTO = new ResponseRecipeDTO(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getCookTime(),
                recipe.getDifficulty(),
                recipe.getNbPerson()
        );

        List<Ingredient> ingredients = ingredientService.getAllByRecipe(recipe);
        List<Step> steps = stepService.getAllByRecipe(recipe);
        List<RecipeCategory> recipeCategories = recipeCategoryService.getAllByRecipe(recipe);

        List<ResponseIngredientDTO> ingredientDTOS = new ArrayList<>();
        List<StepDTO> stepDTOS = new ArrayList<>();
        List<CategoryDTO> categories = new ArrayList<>();

        ingredients.forEach(ingredient ->
                ingredientDTOS.add(
                        new ResponseIngredientDTO(ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit())
                )
        );

        steps.forEach(step ->
                stepDTOS.add(
                        new StepDTO(step.getOrderId(), step.getDescription())
                )
        );

        recipeCategories.forEach(recipeCategory ->
                {
                    Category category = categoryService.getById(recipeCategory.getId().getCategoryId());
                    categories.add(
                            new CategoryDTO(category.getId(), category.getName())
                    );
                }
        );

        return new ResponseFullRecipeDTO(
                responseRecipeDTO,
                ingredientDTOS,
                stepDTOS,
                categories,
                imageBase64
        );
    }
}

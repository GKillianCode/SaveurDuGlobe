package com.killiangodet.recette.recipe.model.response;

import com.killiangodet.recette.category.model.Category;
import com.killiangodet.recette.category.model.CategoryDTO;
import com.killiangodet.recette.image.model.ImageDTO;
import com.killiangodet.recette.ingredient.model.request.IngredientDTO;
import com.killiangodet.recette.ingredient.model.response.ResponseIngredientDTO;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.step.model.StepDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFullRecipeDTO {
    private ResponseRecipeDTO recipe;
    private List<ResponseIngredientDTO> ingredients;
    private List<StepDTO> steps;
    private List<CategoryDTO> categories;
    private ImageDTO image;
}

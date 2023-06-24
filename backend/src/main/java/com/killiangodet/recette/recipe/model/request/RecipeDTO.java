package com.killiangodet.recette.recipe.model.request;

import com.killiangodet.recette.image.model.ImageDTO;
import com.killiangodet.recette.ingredient.model.request.IngredientDTO;
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
public class RecipeDTO {
    private String title;
    private String description;
    private Integer preparationTime;
    private Integer cookTime;
    private Integer difficulty;
    private Integer nbPerson;

    private List<IngredientDTO> ingredients;
    private List<StepDTO> steps;
    private List<Integer> categories;
    private ImageDTO imageDTO;
}

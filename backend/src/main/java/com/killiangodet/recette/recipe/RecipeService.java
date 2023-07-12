package com.killiangodet.recette.recipe;

import com.killiangodet.recette.category.CategoryService;
import com.killiangodet.recette.category.model.Category;
import com.killiangodet.recette.category.model.CategoryDTO;
import com.killiangodet.recette.image.ImageService;
import com.killiangodet.recette.image.model.Image;
import com.killiangodet.recette.image.model.ImageDTO;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.ingredient.model.response.ResponseIngredientDTO;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipe.model.response.ResponseFullRecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseRecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseRecipeWithImageDTO;
import com.killiangodet.recette.recipe.repository.RecipeRepository;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.step.model.Step;
import com.killiangodet.recette.step.model.StepDTO;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
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

    @Autowired
    ImageService imageService;

    @Value("${image_output_format}")
    private String imageOutputFormat;

    @Value("${path_recipe_cover}")
    private String pathRecipeCover;

    public Recipe getRecipeById(Integer recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);

        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new EntityNotFoundException();
        }
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

        if (recipe != null) {
            throw new EntityExistsException("Entity already exists");
        }
    }

    public void remove(Recipe recipe) {
        recipeRepository.deleteById(recipe.getId());
    }

    public ResponseFullRecipeDTO getFullRecipeDTO(Recipe recipe) {
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
        Image image = imageService.getByRecipe(recipe);

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

        String imageBase64 = getImageBase64(image);

        ImageDTO imageDTO = new ImageDTO(
                imageBase64,
                image.getDescription()
        );

        return new ResponseFullRecipeDTO(
                responseRecipeDTO,
                ingredientDTOS,
                stepDTOS,
                categories,
                imageDTO
        );
    }

    public List<ResponseRecipeWithImageDTO> searchRecipesByKeyword(String keyword, int pageId, int size) throws Exception {
        Pageable pageable = PageRequest.of(pageId, size);

        Page<Recipe> recipePage = recipeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        List<Recipe> recipeList = recipePage.getContent();

        List<ResponseRecipeWithImageDTO> responseRecipeDTOS = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            Image imageData = imageService.getByRecipe(recipe);

            String base64 = getImageBase64(imageData);

            ImageDTO imageDTO = new ImageDTO(base64, imageData.getDescription());

            ResponseRecipeWithImageDTO responseRecipeDTO = new ResponseRecipeWithImageDTO(
                    recipe.getId(),
                    recipe.getTitle(),
                    recipe.getDescription(),
                    recipe.getPreparationTime(),
                    recipe.getCookTime(),
                    recipe.getDifficulty(),
                    recipe.getNbPerson(),
                    imageDTO
            );
            responseRecipeDTOS.add(responseRecipeDTO);
        }

        return responseRecipeDTOS;
    }

    private String getImageBase64(Image imageData) {
        if (imageData == null) {
            throw new IllegalArgumentException();
        }

        byte[] imageBytes;
        try {
            Path imagePath = Paths.get(pathRecipeCover, imageData.getFileName() + "." + imageData.getFormat());
            imageBytes = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(imageBytes);
    }

}

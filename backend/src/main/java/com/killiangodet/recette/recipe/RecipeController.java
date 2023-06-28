package com.killiangodet.recette.recipe;

import com.killiangodet.recette.category.CategoryService;
import com.killiangodet.recette.exceptions.ExceptionMessage;
import com.killiangodet.recette.image.ImageService;
import com.killiangodet.recette.image.model.Image;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipe.model.request.RecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseFullRecipeDTO;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.unit.UnitService;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.utils.ImageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipe")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@Tag(name = "Recipe")
@SecurityRequirement(name = "Authorization")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private StepService stepService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    @Value("${speed_max_time_indicator}")
    private Integer speedMaxTimeIndicator;

    @Value("${speed_category_id}")
    private Integer SpeedCategoryId;

    @Value("${image_output_format}")
    private String imageOutputFormat;

    @Value("${path_recipe_cover}")
    private String pathRecipeCover;

    @Operation(summary = "Add recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseFullRecipeDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class})))
    })
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Object> add(Authentication authentication, @Valid @RequestBody RecipeDTO recipeDTO) throws Exception {
        User user = userService.getUserByUsername(authentication.getName());

        // Recipe
        recipeService.checkIfRecipeExists(user, recipeDTO.getTitle());

        Recipe newRecipe = new Recipe(recipeDTO.getTitle(), recipeDTO.getDescription(), recipeDTO.getPreparationTime(),
                recipeDTO.getCookTime(), recipeDTO.getDifficulty(), recipeDTO.getNbPerson(), user);

        try {
            recipeService.save(newRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create recipe");
        }

        Recipe savedRecipe = newRecipe;

        // Ingredients
        try {
            ingredientService.addAllIngredients(recipeDTO.getIngredients(), savedRecipe);
        } catch (Exception e) {
            cleanupRecipe(savedRecipe);
            return ResponseEntity.badRequest().body("Failed to create ingredients");
        }

        // Steps
        try {
            stepService.addAllSteps(recipeDTO.getSteps(), savedRecipe);
        } catch (Exception e) {
            cleanupRecipe(savedRecipe);
            return ResponseEntity.badRequest().body("Failed to create steps");
        }

        // Categories
        try {
            if (savedRecipe.getPreparationTime() + savedRecipe.getCookTime() <= speedMaxTimeIndicator) {
                RecipeCategory rc = new RecipeCategory(
                        new RecipeCategory.RecipeCategoryId(SpeedCategoryId, savedRecipe.getId()));
                recipeCategoryService.save(rc);
            }

            recipeCategoryService.addAllCategories(recipeDTO.getCategories(), savedRecipe);
        } catch (Exception e) {
            cleanupRecipe(savedRecipe);
            return ResponseEntity.badRequest().body("Failed to create recipeCategory");
        }

        // Image
        String fileName = UUID.randomUUID().toString();
        try {
            ImageUtils.createImageFile(recipeDTO.getImageDTO().getImage(), pathRecipeCover, fileName, imageOutputFormat);
            Image image = new Image(fileName, imageOutputFormat, recipeDTO.getImageDTO().getDescription(), savedRecipe);
            imageService.save(image);
        } catch (Exception e) {
            cleanupRecipe(savedRecipe);
            return ResponseEntity.badRequest().body("Failed to create image");
        }

        ResponseFullRecipeDTO responseRecipeDTO = null;

        try {
            Image imageData = imageService.getByRecipe(savedRecipe);
            byte[] imageBytes = Files.readAllBytes(Paths.get(pathRecipeCover + imageData.getFileName() + "." + imageData.getFormat()));

            String base64 = Base64.getEncoder().encodeToString(imageBytes);

            responseRecipeDTO = recipeService.getFullRecipeDTO(savedRecipe, "data:image/jpg;base64,"+base64);

        } catch (Exception e) {
            cleanupRecipe(savedRecipe);
            return ResponseEntity.badRequest().body("Failed to return recipe");
        }

        return ResponseEntity.ok().body(responseRecipeDTO);
    }

    private void cleanupRecipe(Recipe recipe) {
        ingredientService.removeAllByRecipe(recipe);
        stepService.removeAllByRecipe(recipe);
        recipeCategoryService.removeAllByRecipe(recipe);
        imageService.remove(recipe);
        recipeService.remove(recipe);
    }


    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(
            @PathVariable("id") Integer recipeId,
            Authentication authentication
    ){
        try{
            Recipe recipe = recipeService.getRecipeById(recipeId);
            cleanupRecipe(recipe);

        } catch (Exception e){
            return ResponseEntity.badRequest().body("");
        }

        return ResponseEntity.ok("Delete recipe success");
    }

}

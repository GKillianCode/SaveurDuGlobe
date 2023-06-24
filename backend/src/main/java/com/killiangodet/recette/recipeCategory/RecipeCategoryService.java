package com.killiangodet.recette.recipeCategory;

import com.killiangodet.recette.category.CategoryService;
import com.killiangodet.recette.category.model.Category;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipeCategory.model.RecipeCategory;
import com.killiangodet.recette.recipeCategory.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeCategoryService {

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Value("${speed_category_id}")
    private Integer SpeedCategoryId;

    public void save(RecipeCategory rc) {
        recipeCategoryRepository.save(rc);
    }

    public void addAllCategories(List<Integer> categoriesIds, Recipe recipe) {
        List<Category> categories = new ArrayList<>();
        List<RecipeCategory> recipeCategories = new ArrayList<>();

        categoriesIds.forEach(
                category -> {
                    if(category != SpeedCategoryId) categories.add(categoryService.getById(category));
                }
        );

        categories.forEach(
                category -> recipeCategories.add(
                        new RecipeCategory(
                                new RecipeCategory.RecipeCategoryId(
                                        category.getId(), recipe.getId()
                                )
                        )
                )
        );

        recipeCategories.forEach(
                recipeCategory -> recipeCategoryRepository.save(recipeCategory)
        );
    }

    public List<RecipeCategory> getAllByRecipe(Recipe recipe) { return recipeCategoryRepository.findAllByRecipeId(recipe.getId()); }

    public void removeAllByRecipe(Recipe recipe) {
        List<RecipeCategory> recipeCategories = getAllByRecipe(recipe);
        recipeCategoryRepository.deleteAll(recipeCategories);
    }
}

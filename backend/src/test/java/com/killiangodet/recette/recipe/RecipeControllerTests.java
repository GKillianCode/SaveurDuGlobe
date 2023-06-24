package com.killiangodet.recette.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.favoriteRecipe.FavoriteRecipeService;
import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import com.killiangodet.recette.favoriteRecipe.model.request.FavoriteRecipeDTO;
import com.killiangodet.recette.image.ImageService;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.recipe.model.request.RecipeDTO;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "son_goku@gmail.com", password = "SonGoku#1989", roles = {"TEST"})
@Transactional
public class RecipeControllerTests {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ImageService imageService;

    @Autowired
    RecipeCategoryService recipeCategoryService;

    @Autowired
    StepService stepService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Authentication authentication;

    @Value("${test_email}")
    private String email;

    @BeforeEach
    public void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        User user = userService.getUserByUsername(this.email);
        this.authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

//    @Test
//    void testAddRecipe() throws Exception{
//
//        RecipeDTO recipeDTO = new RecipeDTO(
//            // A compléter
//        );
//
//        RequestBuilder request = MockMvcRequestBuilders.post("/api/recipe/add")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(recipeDTO))
//                .principal(authentication);
//        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
//        mockMvc.perform(request)
//                .andExpect(resultStatus);
//
//        // A compléter
//    }
}

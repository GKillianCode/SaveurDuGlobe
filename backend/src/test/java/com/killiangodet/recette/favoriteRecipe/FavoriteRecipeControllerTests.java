package com.killiangodet.recette.favoriteRecipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.favoriteRecipe.model.FavoriteRecipe;
import com.killiangodet.recette.favoriteRecipe.model.request.FavoriteRecipeDTO;
import com.killiangodet.recette.recipe.RecipeService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "son_goku@gmail.com", password = "SonGoku#1989", roles = {"TEST"})
@Transactional
public class FavoriteRecipeControllerTests {

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    FavoriteRecipeService favoriteRecipeService;

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

    /**
     * Test l'ajout d'une recette en recette favorite
     *
     * @throws Exception
     */
    @Test
    void testAddFavoriteRecipe() throws Exception {
        Integer recipeId = 2;
        FavoriteRecipeDTO favoriteRecipeDTO = new FavoriteRecipeDTO(recipeId);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/favorite-recipe/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(favoriteRecipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        User user = userService.getUserByUsername(this.email);

        FavoriteRecipe fv = favoriteRecipeService.findOneFavoriteRecipeByUserIdAndRecipeId(user.getId(), recipeId);

        assertNotNull(fv);
    }

    /**
     * Test l'ajout d'une recette qui n'existe pas en recette favorite
     *
     * @throws Exception
     */
    @Test
    void testFailedAddFavoriteRecipe() throws Exception {
        Integer recipeId = 0;
        FavoriteRecipeDTO favoriteRecipeDTO = new FavoriteRecipeDTO(recipeId);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/favorite-recipe/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(favoriteRecipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isNotFound();
        mockMvc.perform(request)
                .andExpect(resultStatus);
    }

    /**
     * Test la suppression d'une recette favorite
     *
     * @throws Exception
     */
    @Test
    void testDeleteFavoriteRecipe() throws Exception {
        Integer recipeId = 1;
        FavoriteRecipeDTO favoriteRecipeDTO = new FavoriteRecipeDTO(recipeId);

        User user = userService.getUserByUsername(this.email);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/favorite-recipe/remove")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(favoriteRecipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        boolean existBeforeDelete = favoriteRecipeService.existsFavoriteRecipeByUserIdAndRecipeId(user.getId(), recipeId);

        assertFalse(existBeforeDelete);
    }

    /**
     * Test la suppression d'une recette favorite qui n'existe pas
     *
     * @throws Exception
     */
    @Test
    void testFailedDeleteFavoriteRecipe() throws Exception{
        Integer recipeId = 2;
        FavoriteRecipeDTO favoriteRecipeDTO = new FavoriteRecipeDTO(recipeId);

        User user = userService.getUserByUsername(this.email);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/favorite-recipe/remove")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(favoriteRecipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isNotFound();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        boolean existBeforeDelete = favoriteRecipeService.existsFavoriteRecipeByUserIdAndRecipeId(user.getId(), recipeId);
        assertFalse(existBeforeDelete);
    }

}

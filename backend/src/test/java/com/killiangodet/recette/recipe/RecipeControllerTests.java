
package com.killiangodet.recette.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.image.ImageService;
import com.killiangodet.recette.image.model.ImageDTO;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.ingredient.model.request.IngredientDTO;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.recipe.model.request.RecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseFullRecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseRecipeWithImageDTO;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.step.model.StepDTO;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * Vérifie le point d'api "/api/recipe/add" qui permet à un utilisateur de poster
     * une recette
     *
     * @throws Exception
     */
    @Test
    void testAddRecipe() throws Exception {

        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        ingredientDTOS.add(new IngredientDTO("oeufs", 3, 6));
        ingredientDTOS.add(new IngredientDTO("sucre", 290, 10));
        ingredientDTOS.add(new IngredientDTO("sucre vanillé", 1, 25));
        ingredientDTOS.add(new IngredientDTO("beurre", 200, 10));
        ingredientDTOS.add(new IngredientDTO("farine", 200, 10));

        List<StepDTO> stepDTOS = new ArrayList<>();
        stepDTOS.add(new StepDTO(1, "Pesez les ingrédients. Adaptez le poids de chaque ingrédient qui doit être le même que celui des oeufs. En général, 3 oeufs représentent plus ou moins 200 g."));
        stepDTOS.add(new StepDTO(2, "Préchauffez votre four à 180°C. Faites fondre le beurre 30 secondes au micro-ondes."));
        stepDTOS.add(new StepDTO(3, "Mélangez le beurre avec les sucres (fin et vanillé). Incorporez-y petit à petit les oeufs."));
        stepDTOS.add(new StepDTO(4, "Une fois ce mélange effectué, ajoutez petit à petit la farine. Attention : faites-le par petite quantité pour éviter les grumeaux."));
        stepDTOS.add(new StepDTO(5, "Après la farine, incorporez la levure en poudre. Mélangez jusqu'à l'obtention d'un mélange parfait."));
        stepDTOS.add(new StepDTO(6, "Beurrez votre moule, versez-y la pâte et mettez le tout au four. Commencez à surveiller après 40 minutes de cuisson. Tant que le cake ne noircit pas, vous pouvez le laisser au four. Après 60 minutes maximum, le cake est à coup sûr somptueux."));

        List<Integer> categoriesList = new ArrayList<>();
        categoriesList.add(4);

        ImageDTO imageDTO = new ImageDTO(
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK",
                "Image de quatre-quarts moelleux"
        );

        RecipeDTO recipeDTO = new RecipeDTO(
                "Quatre-quarts moelleux facile",
                "Super quatre-quarts moelleux et facile",
                25, 40, 1, 6,
                ingredientDTOS,
                stepDTOS,
                categoriesList,
                imageDTO
        );

        RequestBuilder request = MockMvcRequestBuilders.post("/api/recipe/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(recipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        ResponseFullRecipeDTO responseFullRecipeDTO = objectMapper.readValue(contentAsString, ResponseFullRecipeDTO.class);

        assertNotNull(responseFullRecipeDTO);
        assertEquals(recipeDTO.getTitle(), responseFullRecipeDTO.getRecipe().getTitle());
        assertEquals(recipeDTO.getDescription(), responseFullRecipeDTO.getRecipe().getDescription());
        assertEquals(recipeDTO.getPreparationTime(), responseFullRecipeDTO.getRecipe().getPreparationTime());
        assertEquals(recipeDTO.getCookTime(), responseFullRecipeDTO.getRecipe().getCookTime());
        assertEquals(recipeDTO.getNbPerson(), responseFullRecipeDTO.getRecipe().getNbPerson());
        assertEquals(recipeDTO.getImageDTO().getDescription(), responseFullRecipeDTO.getImage().getDescription());
    }

    @Test
    void testFailedAddRecipeWithBadImageFormat() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO(
                "Quatre-quarts moelleux facile",
                "Super quatre-quarts moelleux et facile",
                25, 40, 1, 6,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ImageDTO(
                        "data:image/webp;base64,iVBORw0KGgoAAAANSUhEUgAAABgAAAAYC=",
                        "Image de quatre-quarts moelleux"
                )
        );

        RequestBuilder request = MockMvcRequestBuilders.post("/api/recipe/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(recipeDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().is5xxServerError();
        mockMvc.perform(request)
                .andExpect(resultStatus);
    }

    @Test
    void testDeleteRecipe() throws Exception {
        int id = 3;

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/recipe/{id}/delete", id)
                .principal(authentication);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        assertThrows(EntityNotFoundException.class, () -> recipeService.getRecipeById(id));
    }

//    @Test
//    void testFailedDeleteRecipe() throws Exception {
//        int id = -15;
//
//        RequestBuilder request = MockMvcRequestBuilders.delete("/api/recipe/{id}/delete", id)
//                .principal(authentication);
//
//        ResultMatcher resultStatus = MockMvcResultMatchers.status().is4xxClientError();
//        mockMvc.perform(request)
//                .andExpect(resultStatus);
//
//        assertThrows(EntityNotFoundException.class, () -> recipeService.getRecipeById(id));
//    }

    @Test
    void testSearchRecipesByKeyword() throws Exception {
        String keyword = "mousse";
        int pageId = 0;
        int nbResultPerPage = 1;

        ImageDTO image = new ImageDTO("/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAYABgDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDqdd8SaxPd3lnLO0UIkKeUihcAHpnr+tVvtF7/AMI5u86TZ5+3O7nbj19M1u65FpyRT3mphUWM/PKAcjnHbk8ms3/hJvDP2HyP7Qh8jbt24bOPpjNfOVcxVTlag9GfURrUYQjGMUhuheJNYgu7OzinaWEyBPKdQ2QT0z1/WitTQ4tOeKC80wK6yH5JSDk8478jkUV1U8zUrpRehxYxUqk1KEUv68jotd0JL+GdTEJYJlIlj7/UV5r/AMKtt/tn/H7ceTn/AFXlDfj03f8A1qKK5sxoxo1b03bm3OXDzc42l0PStC0JLCGBREIoIVAij7/U0UUV7GEw8KNJKPXU461SU5XZ/9k=",
                "Photo de la mousse au chocolat"
        );

        ResponseRecipeWithImageDTO responseRecipeWithImageDTO = new ResponseRecipeWithImageDTO(
                1,
                "Mousse au chocolat",
                "Mousse au chosolat facile et délicieuse",
                20, 0, 1, 2,
                image
        );

        RequestBuilder request = MockMvcRequestBuilders.get("/api/recipe/recipes?keyword=" + keyword + "&pageId=" + pageId + "&nbResultPerPage=" + nbResultPerPage)
                .principal(authentication);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<ResponseRecipeWithImageDTO> responses = Arrays.asList(objectMapper.readValue(contentAsString, ResponseRecipeWithImageDTO[].class));

        assertNotNull(responses);
        assert responses.size() > 0;
        assert responses.get(0).equals(responseRecipeWithImageDTO);
    }

    @Test
    void testSearchRecipesWithIncorrectKeyword() throws Exception {
        String keyword = "abcd";
        int pageId = 0;
        int nbResultPerPage = 1;

        RequestBuilder request = MockMvcRequestBuilders.get("/api/recipe/recipes?keyword=" + keyword + "&pageId=" + pageId + "&nbResultPerPage=" + nbResultPerPage)
                .principal(authentication);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<ResponseRecipeWithImageDTO> responses = Arrays.asList(objectMapper.readValue(contentAsString, ResponseRecipeWithImageDTO[].class));

        assertNotNull(responses);
        assert responses.size() == 0;
    }

    @Test
    void testGetOneFullRecipe() throws Exception {
        int recipeId = 1;
        Recipe recipe = recipeService.getRecipeById(recipeId);
        ResponseFullRecipeDTO responseFullRecipeDTO = recipeService.getFullRecipeDTO(recipe);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/recipe/"+recipeId)
                .principal(authentication);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        ResponseFullRecipeDTO response = objectMapper.readValue(contentAsString, ResponseFullRecipeDTO.class);

        assertNotNull(response);
        assert response.equals(responseFullRecipeDTO);
    }
}
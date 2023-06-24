package com.killiangodet.recette.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.image.ImageService;
import com.killiangodet.recette.image.model.ImageDTO;
import com.killiangodet.recette.ingredient.IngredientService;
import com.killiangodet.recette.ingredient.model.request.IngredientDTO;
import com.killiangodet.recette.recipe.model.request.RecipeDTO;
import com.killiangodet.recette.recipe.model.response.ResponseFullRecipeDTO;
import com.killiangodet.recette.recipeCategory.RecipeCategoryService;
import com.killiangodet.recette.step.StepService;
import com.killiangodet.recette.step.model.StepDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    /**
     * Vérifie le point d'api "/api/recipe/add" qui permet à un utilisateur de poster
     * une recette
     *
     * @throws Exception
     */
    @Test
    void testAddRecipe() throws Exception{

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
    void testFailedAddRecipeWithBadImageFormat() throws Exception{
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
}

package com.killiangodet.recette.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.comment.model.Comment;
import com.killiangodet.recette.comment.model.request.CommentDTO;
import com.killiangodet.recette.comment.model.request.CommentDeleteDTO;
import com.killiangodet.recette.recipe.RecipeService;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
public class CommentTests {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

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

    @Test
    void testAddComment() throws Exception {
        Integer recipeId = 2;
        CommentDTO commentDTO = new CommentDTO("Superbe recette !", 5, recipeId);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/comment/post")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        User user = userService.getUserByUsername(this.email);
        Comment comment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipeId);

        CommentDTO newCommentDTO = new CommentDTO(comment.getDescription(), comment.getRating().getId(), comment.getRecipe().getId());

        assertEquals(commentDTO, newCommentDTO);
    }

    @Test
    void testUpdateComment() throws Exception {
        Integer recipeId = 1;
        User user = userService.getUserByUsername(this.email);
        Comment comment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipeId);

        CommentDTO originalCommentDTO = new CommentDTO(comment.getDescription(), comment.getRating().getId(), comment.getRecipe().getId());
        CommentDTO updatedCommentDTO = new CommentDTO("Bonne Recette", 4, 1);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/comment/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedCommentDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        Comment updatedComment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipeId);

        CommentDTO newCommentDTO = new CommentDTO(updatedComment.getDescription(), updatedComment.getRating().getId(), updatedComment.getRecipe().getId());

        assertNotEquals(originalCommentDTO, newCommentDTO);
    }

    @Test
    void testDeleteCommentWithExistsComment() throws Exception {
        Integer recipeId = 1;
        User user = userService.getUserByUsername(this.email);
        Comment comment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipeId);
        CommentDeleteDTO commentDeleteDTO = new CommentDeleteDTO(recipeId);
        Recipe recipe = recipeService.getRecipeById(recipeId);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/comment/delete")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDeleteDTO))
                .principal(authentication);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus);

        Boolean isExistsComment = commentService.existsCommentByUserAndRecipe(user, recipe);

        assertFalse(isExistsComment);
    }


}

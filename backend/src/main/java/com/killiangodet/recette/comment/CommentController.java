package com.killiangodet.recette.comment;

import com.killiangodet.recette.comment.model.Comment;
import com.killiangodet.recette.comment.model.request.CommentDTO;
import com.killiangodet.recette.rating.RatingService;
import com.killiangodet.recette.rating.model.Rating;
import com.killiangodet.recette.rating.repository.RatingRepository;
import com.killiangodet.recette.recipe.RecipeService;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@Tag(name = "Comment")
@SecurityRequirement(name = "Authorization")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    RatingService ratingService;

    @PostMapping("/post")
    public ResponseEntity<String> postComment(Authentication authentication, @RequestBody CommentDTO commentDTO){
        Recipe recipe = recipeService.getRecipeById(commentDTO.getRecipeId());
        Rating rating = ratingService.getRatingById(commentDTO.getRatingId());
        User user = userService.getUserByUsername(authentication.getName());

        Comment comment = new Comment(
                commentDTO.getDescription(),
                user,
                recipe,
                rating
        );

        commentService.saveComment(comment);
        return ResponseEntity.ok("Le commentaire a été posté.");
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateComment(Authentication authentication, @RequestBody CommentDTO commentDTO){
        Recipe recipe = recipeService.getRecipeById(commentDTO.getRecipeId());
        Rating rating = ratingService.getRatingById(commentDTO.getRatingId());
        User user = userService.getUserByUsername(authentication.getName());
        Comment comment = commentService.getCommentByUserAndRecipe(user, recipe);

        comment.setDescription(commentDTO.getDescription());
        comment.setRating(rating);

        commentService.saveComment(comment);

        return ResponseEntity.ok("Le commentaire a été mis à jour.");
    }
}

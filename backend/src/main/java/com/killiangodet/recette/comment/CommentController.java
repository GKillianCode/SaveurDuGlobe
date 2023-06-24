package com.killiangodet.recette.comment;

import com.killiangodet.recette.comment.model.Comment;
import com.killiangodet.recette.comment.model.request.CommentDTO;
import com.killiangodet.recette.comment.model.request.CommentDeleteDTO;
import com.killiangodet.recette.rating.RatingService;
import com.killiangodet.recette.rating.model.Rating;
import com.killiangodet.recette.rating.repository.RatingRepository;
import com.killiangodet.recette.recipe.RecipeService;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.UserService;
import com.killiangodet.recette.user.model.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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

    /**
     * Ajoute un commentaire
     *
     * @param authentication
     * @param commentDTO
     * @return "Le commentaire a été posté." en cas de réussite
     */
    @PostMapping("/post")
    public ResponseEntity<String> postComment(Authentication authentication, @Valid @RequestBody CommentDTO commentDTO){
        Recipe recipe = recipeService.getRecipeById(commentDTO.getRecipeId());
        Rating rating = ratingService.getRatingById(commentDTO.getRatingId());
        User user = userService.getUserByUsername(authentication.getName());
        Boolean isExistsComment = commentService.existsCommentByUserAndRecipe(user, recipe);

        if(isExistsComment){
            throw new EntityExistsException();
        }

        Comment comment = new Comment(
                commentDTO.getDescription(),
                user,
                recipe,
                rating
        );

        commentService.saveComment(comment);
        return ResponseEntity.ok("Le commentaire a été posté.");
    }

    /**
     * Met à jour le description et la note d'un commentaire, un utilisateur ne peut mettre
     * qu'un seul commentaire par recette.
     *
     * @param authentication
     * @param commentDTO
     * @return "Le commentaire a été mis à jour." en cas de réussite.
     */
    @PatchMapping("/update")
    public ResponseEntity<String> updateComment(Authentication authentication, @RequestBody CommentDTO commentDTO){
        Rating rating = ratingService.getRatingById(commentDTO.getRatingId());
        Recipe recipe = recipeService.getRecipeById(commentDTO.getRecipeId());
        User user = userService.getUserByUsername(authentication.getName());
        Boolean isExistsComment = commentService.existsCommentByUserAndRecipe(user, recipe);

        if(!isExistsComment){
            throw new EntityNotFoundException();
        }

        Comment comment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipe.getId());

        comment.setDescription(commentDTO.getDescription());
        comment.setRating(rating);

        commentService.saveComment(comment);

        return ResponseEntity.ok("Le commentaire a été mis à jour.");
    }

    /**
     * Supprime un commentaire à partir de l'utilisateur qui là porté.
     *
     * @param authentication
     * @param commentDeleteDTO
     * @return "Le commentaire à été supprimé !" en cas de réussite.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteComment(Authentication authentication, @RequestBody CommentDeleteDTO commentDeleteDTO){
        User user = userService.getUserByUsername(authentication.getName());
        Recipe recipe = recipeService.getRecipeById(commentDeleteDTO.getRecipeId());
        Boolean isExistsComment = commentService.existsCommentByUserAndRecipe(user, recipe);

        if(!isExistsComment){
            throw new EntityNotFoundException();
        }

        Comment comment = commentService.getCommentByUserIdAndRecipeId(user.getId(), recipe.getId());

        commentService.deleteComment(comment);

        return ResponseEntity.ok("Le commentaire à été supprimé !");
    }
}

package com.killiangodet.recette.comment.repository;

import com.killiangodet.recette.comment.model.Comment;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findOneCommentByUserAndRecipe(User user, Recipe recipe);

    Comment findOneCommentByUserIdAndRecipeId(Integer userId, Integer recipeId);
}

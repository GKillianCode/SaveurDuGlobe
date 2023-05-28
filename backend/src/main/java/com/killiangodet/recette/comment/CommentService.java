package com.killiangodet.recette.comment;

import com.killiangodet.recette.comment.model.Comment;
import com.killiangodet.recette.comment.repository.CommentRepository;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public Boolean existsCommentByUserAndRecipe(User user, Recipe recipe) {
        Comment comment = commentRepository.findOneCommentByUserAndRecipe(user, recipe);
        if(comment == null){
            return false;
        }
        return true;
    }

    public Comment getCommentByUserIdAndRecipeId(Integer userId, Integer recipeId) {
        Comment comment = commentRepository.findOneCommentByUserIdAndRecipeId(userId, recipeId);
        if(comment == null){
            throw new EntityNotFoundException();
        }

        return comment;
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}

package com.killiangodet.recette.comment.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of={"description", "ratingId", "recipeId"})
public class CommentDTO {

    @Size(min = 0, max = 350, message = "Comment not valid")
    private String description;

    @Min(value = 1, message = "Rating not valid")
    @Max(value = 5, message = "Rating not valid")
    private Integer ratingId;

    @Min(value = 1, message = "RecipeId not valid")
    private Integer recipeId;
}

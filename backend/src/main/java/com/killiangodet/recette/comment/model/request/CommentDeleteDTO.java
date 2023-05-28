package com.killiangodet.recette.comment.model.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDeleteDTO {

    @Min(value = 0)
    Integer recipeId;

}

package com.killiangodet.recette.recipe.model.response;

import com.killiangodet.recette.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title", "description", "preparationTime", "cookTime", "difficulty", "nbPerson"})
public class ResponseRecipeDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer preparationTime;
    private Integer cookTime;
    private Integer difficulty;
    private Integer nbPerson;
}

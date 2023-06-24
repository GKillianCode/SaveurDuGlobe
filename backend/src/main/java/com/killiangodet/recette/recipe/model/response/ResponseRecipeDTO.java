package com.killiangodet.recette.recipe.model.response;

import com.killiangodet.recette.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseRecipeDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer preparationTime;
    private Integer cookTime;
    private Integer difficulty;
    private Integer nbPerson;
}

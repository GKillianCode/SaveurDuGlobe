package com.killiangodet.recette.recipe.model.response;

import com.killiangodet.recette.image.model.ImageDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "title", "description", "preparationTime", "cookTime", "difficulty", "nbPerson"})
public class ResponseRecipeWithImageDTO {
    private Integer id;
    private String title;
    private String description;
    private Integer preparationTime;
    private Integer cookTime;
    private Integer difficulty;
    private Integer nbPerson;
    private ImageDTO image;
}

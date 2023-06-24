package com.killiangodet.recette.ingredient.model.response;

import com.killiangodet.recette.unit.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseIngredientDTO {

    private String name;

    private Integer quantity;

    private Unit unit;
}

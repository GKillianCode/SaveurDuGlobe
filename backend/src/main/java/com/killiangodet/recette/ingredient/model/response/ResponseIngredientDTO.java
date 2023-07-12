package com.killiangodet.recette.ingredient.model.response;

import com.killiangodet.recette.unit.model.Unit;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "quantity", "unit"})
public class ResponseIngredientDTO {
    private String name;
    private Integer quantity;
    private Unit unit;
}

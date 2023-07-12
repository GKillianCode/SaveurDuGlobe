package com.killiangodet.recette.category.model;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
public class CategoryDTO {
    private Integer id;
    private String name;
}

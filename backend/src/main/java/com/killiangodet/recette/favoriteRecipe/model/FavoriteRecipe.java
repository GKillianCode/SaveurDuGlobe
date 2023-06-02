package com.killiangodet.recette.favoriteRecipe.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Entity
@Table(name = "favoriterecipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@EnableJpaRepositories
public class FavoriteRecipe {
    @EmbeddedId
    private FavoriteRecipeId id;
}




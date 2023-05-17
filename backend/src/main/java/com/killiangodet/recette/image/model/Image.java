package com.killiangodet.recette.image.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Integer id;

    @Column(name = "img_name")
    private String name;

    @Column(name = "img_description")
    private String description;

    @Column(name = "img_format")
    private String format;

    @Column(name = "img_recipeId")
    private Integer recipeId;
}

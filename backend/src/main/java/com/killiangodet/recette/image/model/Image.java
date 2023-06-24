package com.killiangodet.recette.image.model;

import com.killiangodet.recette.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Integer id;

    @Column(name = "img_file_name")
    private String fileName;

    @Column(name = "img_format")
    private String format;

    @Column(name = "img_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "img_recipe_id")
    private Recipe recipe;

    public Image(String fileName, String format, String description, Recipe recipe){
        this.fileName = fileName;
        this.format = format;
        this.description = description;
        this.recipe = recipe;
    }
}

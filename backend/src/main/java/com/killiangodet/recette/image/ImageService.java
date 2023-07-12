package com.killiangodet.recette.image;

import com.killiangodet.recette.image.model.Image;
import com.killiangodet.recette.image.repository.ImageRepository;
import com.killiangodet.recette.recipe.model.Recipe;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;
    @Value("${path_recipe_cover}")
    private String pathRecipeCover;

    public void save(Image image) {
        imageRepository.save(image);
    }

    public void remove(Recipe recipe) {
        Image image = imageRepository.findOneByRecipe(recipe);

        File fichier = new File(pathRecipeCover+image.getFileName()+"."+image.getFormat());
        fichier.delete();

        imageRepository.delete(image);
    }

    public Image getByRecipe(Recipe recipe) {
        return imageRepository.findOneByRecipe(recipe);
    }
}

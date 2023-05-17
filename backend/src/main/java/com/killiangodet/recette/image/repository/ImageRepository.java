package com.killiangodet.recette.image.repository;

import com.killiangodet.recette.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}

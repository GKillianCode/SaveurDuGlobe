package com.killiangodet.recette.rating.repository;

import com.killiangodet.recette.rating.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}

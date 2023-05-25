package com.killiangodet.recette.rating;

import com.killiangodet.recette.rating.model.Rating;
import com.killiangodet.recette.rating.repository.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    RatingRepository ratingRepository;

    public Rating getRatingById(Integer ratingId) {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        if(rating.isPresent()){
            return rating.get();
        }
        throw new EntityNotFoundException();
    }
}

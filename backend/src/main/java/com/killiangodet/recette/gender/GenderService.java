package com.killiangodet.recette.gender;

import com.killiangodet.recette.gender.model.Gender;
import com.killiangodet.recette.gender.repository.GenderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    GenderRepository genderRepository;

    public Gender getGenderById(Integer genderId){
        Optional<Gender> gender = genderRepository.findById(genderId);
        if(gender.isPresent()){
            return gender.get();
        } else {
            throw new EntityNotFoundException("Gender %s doesn't exists".formatted(genderId));
        }
    }
}

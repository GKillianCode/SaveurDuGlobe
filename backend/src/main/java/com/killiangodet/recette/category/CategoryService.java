package com.killiangodet.recette.category;

import com.killiangodet.recette.category.model.Category;
import com.killiangodet.recette.category.repository.CategoryRepository;
import com.killiangodet.recette.recipe.model.Recipe;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return category.get();
        }

        throw new EntityNotFoundException();
    }
}

package com.killiangodet.recette.step;

import com.killiangodet.recette.ingredient.model.Ingredient;
import com.killiangodet.recette.recipe.model.Recipe;
import com.killiangodet.recette.step.model.Step;
import com.killiangodet.recette.step.model.StepDTO;
import com.killiangodet.recette.step.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StepService {

    @Autowired
    StepRepository stepRepository;

    public void save(Step step) {
        stepRepository.save(step);
    }

    public void addAllSteps(List<StepDTO> stepDTOS, Recipe recipe) throws Exception{
        List<Step> steps = new ArrayList<>();
        stepDTOS.forEach(
                step -> steps.add(
                        new Step(
                                step.getOrderId(),
                                step.getDescription(),
                                recipe
                        )
                )
        );
        steps.forEach(
                this::save
        );
    }

    public List<Step> getAllByRecipe(Recipe recipe) {
        return stepRepository.findAllByRecipeOrderByOrderId(recipe);
    }

    public void removeAll(List<Step> steps) {
        stepRepository.deleteAll(steps);
    }

    public void removeAllByRecipe(Recipe recipe){
        List<Step> steps = getAllByRecipe(recipe);
        removeAll(steps);
    }
}

package com.killiangodet.recette.unit;

import com.killiangodet.recette.unit.model.Unit;
import com.killiangodet.recette.unit.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UnitService {

    @Autowired
    UnitRepository unitRepository;

    public Unit getUnitById(Integer unitId) {
        Optional<Unit> unit = unitRepository.findById(unitId);

        if(unit.isPresent()){
            return unit.get();
        }

        throw new EntityNotFoundException();
    }
}

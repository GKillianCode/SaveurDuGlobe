package com.killiangodet.recette.gender.repository;

import com.killiangodet.recette.gender.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Integer> {
}

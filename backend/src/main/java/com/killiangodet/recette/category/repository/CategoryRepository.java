package com.killiangodet.recette.category.repository;

import com.killiangodet.recette.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

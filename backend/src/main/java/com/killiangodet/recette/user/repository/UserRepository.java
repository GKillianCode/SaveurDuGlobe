package com.killiangodet.recette.user.repository;

import com.killiangodet.recette.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

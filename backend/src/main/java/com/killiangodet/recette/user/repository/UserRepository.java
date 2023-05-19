package com.killiangodet.recette.user.repository;

import com.killiangodet.recette.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findOneUserByEmailAndPassword(String email, String password);
}

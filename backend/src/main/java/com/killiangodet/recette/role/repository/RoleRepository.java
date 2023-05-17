package com.killiangodet.recette.role.repository;

import com.killiangodet.recette.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

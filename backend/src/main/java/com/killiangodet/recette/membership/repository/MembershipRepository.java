package com.killiangodet.recette.membership.repository;

import com.killiangodet.recette.membership.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
}

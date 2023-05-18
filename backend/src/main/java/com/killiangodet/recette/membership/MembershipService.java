package com.killiangodet.recette.membership;

import com.killiangodet.recette.membership.model.Membership;
import com.killiangodet.recette.membership.repository.MembershipRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MembershipService {

    @Autowired
    MembershipRepository membershipRepository;

    public Membership getMembershipById(Integer membershipId){
        Optional<Membership> membership = membershipRepository.findById(membershipId);
        if(membership.isPresent()){
            return membership.get();
        } else {
            throw new EntityNotFoundException("Membership %s doesn't exists".formatted(membershipId));
        }
    }
}

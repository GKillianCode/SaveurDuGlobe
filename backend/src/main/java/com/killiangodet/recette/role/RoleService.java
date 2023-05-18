package com.killiangodet.recette.role;

import com.killiangodet.recette.role.model.Role;
import com.killiangodet.recette.role.repository.RoleRepository;
import com.killiangodet.recette.user.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getRoleById(Integer roleId){
        Optional<Role> role = roleRepository.findById(roleId);
        if(role.isPresent()){
            return role.get();
        } else {
            throw new EntityNotFoundException("Role %s doesn't exists".formatted(roleId));
        }
    }
}

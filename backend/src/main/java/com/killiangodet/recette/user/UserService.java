package com.killiangodet.recette.user;

import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.UserDTO;
import com.killiangodet.recette.user.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        return userRepository.save(userDTO.toEntity());
    }
}

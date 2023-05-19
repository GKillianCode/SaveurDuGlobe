package com.killiangodet.recette.user;

import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        return userRepository.save(userDTO.toEntity());
    }

    public User getUserByEmailAndPassword(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findOneUserByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        if(user.isPresent()){
            return user.get();
        } else {
            throw new EntityNotFoundException("User with email %s not found".formatted(userLoginDTO.getEmail()));
        }
    }
}

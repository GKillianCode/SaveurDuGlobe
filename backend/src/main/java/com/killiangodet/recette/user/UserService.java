package com.killiangodet.recette.user;

import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.model.response.UserAdminResponseDTO;
import com.killiangodet.recette.user.model.response.UserResponseDTO;
import com.killiangodet.recette.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public List<UserAdminResponseDTO> getAllUserWithLimit(Integer nbResultPerPage, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, nbResultPerPage);
        List<User> users = userRepository.findAll(pageable).stream().toList();
        List<UserAdminResponseDTO> userAdminResponseDTO = new ArrayList<>();

        users.forEach(user -> userAdminResponseDTO
                        .add(new UserAdminResponseDTO(user.getId(), user.getRoleId(), user.getGenderId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getDateOfBirth(), user.getMembershipId())));

        return userAdminResponseDTO;
    }
}

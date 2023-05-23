package com.killiangodet.recette.user;

import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.model.response.UserAdminResponseDTO;
import com.killiangodet.recette.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Va chercher un utilisateur dans la base de données grâce à l'email.
     *
     * @param userLoginDTO
     * @return l'utilisateur si il est trouvé.
     */
    public User getUserByEmailAndPassword(UserLoginDTO userLoginDTO) {
        Optional<User> user = userRepository.findOneUserByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        if(user.isPresent()){
            return user.get();
        } else {
            throw new EntityNotFoundException("User with email %s not found".formatted(userLoginDTO.getUsername()));
        }
    }

    /**
     * Retourne la liste des utilisateur par page et avec un nombre de résultat par page
     *
     * @param nbResultPerPage: Nombre de résultat par page
     * @param pageNumber: Numéro de page
     * @return la liste des utilisateurs
     */
    public List<UserAdminResponseDTO> getAllUserWithLimit(Integer nbResultPerPage, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, nbResultPerPage);
        List<User> users = userRepository.findAll(pageable).stream().toList();
        List<UserAdminResponseDTO> userAdminResponseDTO = new ArrayList<>();

        users.forEach(user -> userAdminResponseDTO
                        .add(new UserAdminResponseDTO(user.getId(), user.getRole().getId(), user.getGenderId(), user.getFirstname(), user.getLastname(), user.getPseudo(), user.getUsername(), user.getDateOfBirth(), user.getMembershipId())));

        return userAdminResponseDTO;
    }

    /**
     * Vérifie si la date est inférieur à la date actuelle.
     *
     * @param date: date à vérifier
     * @return true si la date est inférieur à la date actuelle sinon false
     */
    public static boolean isDateValid(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate);
    }

    /**
     * Vérifie si le mot de passe contient au moins une lettre minuscule, une lettre majuscule,
     * un chiffre, et un caractère spécial.
     *
     * @param password: Mot de passe à vérifier
     * @return true si le mot de passe correspond au critères sinon false
     */
    public static boolean isPasswordValid(String password) {
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*\\d.*")) {
            return false;
        }

        if (!password.matches(".*[@$!%*?&#(){}|€,.;^<>+=\\-_/].*")) {
            return false;
        }

        if (password.length() < 8 || password.length() > 50) {
            return false;
        }

        return true;
    }


    /**
     * Enregistre un utilisateur dans la base de données
     *
     * @param newUser: l'utilisateur à enregistrer
     */
    public void save(User newUser) {
        userRepository.save(newUser);
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new EntityNotFoundException();
        }

        return user;
    }
}

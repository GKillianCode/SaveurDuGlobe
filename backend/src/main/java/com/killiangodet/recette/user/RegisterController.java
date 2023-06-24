package com.killiangodet.recette.user;

// import com.killiangodet.recette.config.JWTTokenProvider;
import com.killiangodet.recette.role.model.Role;
import com.killiangodet.recette.role.repository.RoleRepository;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;

@RestController
public class RegisterController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {

        Optional<Role> role = roleRepository.findById(3);
        if (role.isPresent()) {
            if(!UserService.isDateValid(userDTO.getDateOfBirth())){
                throw new IllegalArgumentException();
            }

            if(!UserService.isPasswordValid(userDTO.getPassword())){
                throw new IllegalArgumentException();
            }

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
            String password = bCryptPasswordEncoder.encode(userDTO.getPassword());

            userService.save(new User(
                    userDTO.getGenderId(),
                    userDTO.getFirstname(),
                    userDTO.getLastname(),
                    userDTO.getPseudo(),
                    userDTO.getDateOfBirth(),
                    userDTO.getUsername(),
                    password,
                    2,
                    role.get()
            ));

            return ResponseEntity.ok("Le compte a été créé !");

        } else {
            return null;
        }
    }
}

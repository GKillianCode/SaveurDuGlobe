package com.killiangodet.recette.user;

import com.killiangodet.recette.gender.GenderService;
import com.killiangodet.recette.membership.MembershipService;
import com.killiangodet.recette.role.RoleService;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.UserDTO;
import com.killiangodet.recette.user.model.UserResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@SecurityRequirement(name = "Authorization")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserDTO userDTO) throws InstanceAlreadyExistsException {
        userService.saveUser(userDTO);

        return ResponseEntity.ok(new UserResponseDTO(
                userDTO.getGenderId(),
                userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getDateOfBirth()
        ));
    }
}

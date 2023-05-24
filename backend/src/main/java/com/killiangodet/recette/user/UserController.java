package com.killiangodet.recette.user;

import com.killiangodet.recette.exceptions.ExceptionMessage;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserChangePasswordDTO;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.model.response.UserAdminResponseDTO;
import com.killiangodet.recette.user.model.response.UserLoginResponseDTO;
import com.killiangodet.recette.user.model.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@SecurityRequirement(name = "Authorization")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Retourne une liste d'utilisateur sous la forme UserAdminResponseDTO
     *
     * @param nbResultPerPage: Nombre de résultat par page
     * @param offset: Numéro de page
     * @return une liste d'utilisateur sous la forme UserAdminResponseDTO
     */
    //@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_TEST')")
    @GetMapping("/all")
    public List<UserAdminResponseDTO> getAll(@RequestParam Integer nbResultPerPage, @RequestParam Integer offset) {
        return userService.getAllUserWithLimit(nbResultPerPage, offset);
    }

    /**
     * Permet à l'utilisateur de changer son mot de passe.
     *
     * @param authentication: Identification de l'utilisateur
     * @param userChangePasswordDTO: Objet contenant l'ancien et le nouveau mot de passe.
     * @return "Le mot de passe a été mis à jour." en cas de succès
     */
    @PatchMapping("/change_password")
    public ResponseEntity<String> changePassword(Authentication authentication, @RequestBody UserChangePasswordDTO userChangePasswordDTO) {
        String oldPassword = userChangePasswordDTO.getOldPassword();
        String newPassword = userChangePasswordDTO.getNewPassword();

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("Le nouveau mot de passe doit être différent de l'ancien.");
        }

        if (!UserService.isPasswordValid(newPassword)) {
            throw new IllegalArgumentException("Le nouveau mot de passe ne respecte pas les critères de validité.");
        }

        User user = userService.getUserByUsername(authentication.getName());
        String encryptedPassword = user.getPassword();


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        if (!bCryptPasswordEncoder.matches(oldPassword, encryptedPassword)) {
            throw new IllegalArgumentException("L'ancien mot de passe est incorrect.");
        }

        String password = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(password);
        userService.save(user);

        return ResponseEntity.ok("Le mot de passe a été mis à jour.");
    }

}

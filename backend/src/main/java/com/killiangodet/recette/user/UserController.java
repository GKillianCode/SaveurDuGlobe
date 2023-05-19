package com.killiangodet.recette.user;

import com.killiangodet.recette.exceptions.ExceptionMessage;
import com.killiangodet.recette.user.model.User;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
@SecurityRequirement(name = "Authorization")
public class UserController {

    @Autowired
    UserService userService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "404", description = "User already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "409", description = "Same name already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class})))
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserDTO userDTO) throws InstanceAlreadyExistsException {
        User user = userService.saveUser(userDTO);

        return ResponseEntity.ok(new UserResponseDTO(
                userDTO.getGenderId(),
                userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getDateOfBirth(),
                user.getMembershipId()
        ));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid supplied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "409", description = "Same name already exists", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class}))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(oneOf = {
                    ExceptionMessage.class})))
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        userService.getUserByEmailAndPassword(userLoginDTO);
        return ResponseEntity.ok(new UserLoginResponseDTO("TokenDeConnexion"));
    }

    @PostMapping("/all")
    public List<UserAdminResponseDTO> getAll(@RequestParam Integer nbResultPerPage, @RequestParam Integer offset) {
        return userService.getAllUserWithLimit(nbResultPerPage, offset);
    }
}

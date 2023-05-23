package com.killiangodet.recette.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.config.AccountCredentials;
import com.killiangodet.recette.role.RoleService;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.model.request.UserChangePasswordDTO;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.model.response.UserAdminResponseDTO;
import com.killiangodet.recette.user.model.response.UserLoginResponseDTO;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String bearerToken;

    @BeforeEach
    public void setUp() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

        this.bearerToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb25fZ29rdUBnbWFpbC5jb20iLCJhdXRoIjoiVEVTVCIsImV4cCI6MTY4NDg5MjQ1N30.bzb8PzOHhcglsDYhraqN5YZZ_sdC6dgD4abLRIDC6Vk";

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    /**
     * Vérifie le point d'api "/register" qui permet à un utilisateur de se créer un compte
     *
     * @throws Exception
     */
    @Test
    void testRegisterUser() throws  Exception {
        UserDTO userDTO = new UserDTO("Léonardo", "Da vinci", "DaVinci", "davinci@gmail.com", "GJn#xT*Uz9XcqfGX#T4f", LocalDate.of(1452, 4, 14), 1);

        RequestBuilder request = MockMvcRequestBuilders.post("/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        assert contentAsString.equals("Le compte a été créé !");
    }

    /**
     * Verifie le point d'api "/api/user/all" qui retourne la liste des utilisateurs (informations restraintes).
     *
     * @throws Exception
     */
    @Test
    void testGetAllUsersWithParameters() throws Exception {
        UserAdminResponseDTO userAdminResponseDTO = new UserAdminResponseDTO(
                2, 4, 1, "Son", "Gokû", "Gokû", "son_goku@gmail.com", LocalDate.of(1989,4,26), 2
        );

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/all?nbResultPerPage=5&offset=0")
                .contentType("application/json")
                .header("Authorization", "Bearer " + this.bearerToken);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<UserAdminResponseDTO> userAdminResponseDTOS = Arrays.asList(objectMapper.readValue(contentAsString, UserAdminResponseDTO[].class));
        assert userAdminResponseDTOS.contains(userAdminResponseDTO);
    }

    /**
     * Vérifier le point d'api "/api/user/change_password" qui permet à un utilisateur
     * de changer son mot de passe.
     *
     * @throws Exception
     */
    @Test
    void testChangePassword() throws Exception {
        String email = "son_goku@gmail.com";
        String oldPassword = "SonGoku#1989";
        String newPassword = "SonGoku#19891";

        User user = userService.getUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        UserChangePasswordDTO userChangePasswordDTO = new UserChangePasswordDTO(oldPassword, newPassword);

        RequestBuilder request = MockMvcRequestBuilders.patch("/api/user/change_password")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userChangePasswordDTO))
                .principal(authentication)
                .header("Authorization", "Bearer " + this.bearerToken);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        User userWithNewPassword = userService.getUserByUsername(email);

        assertTrue(bCryptPasswordEncoder.matches(newPassword, userWithNewPassword.getPassword()));
    }

}

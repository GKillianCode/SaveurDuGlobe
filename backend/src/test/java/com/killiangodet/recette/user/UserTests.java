package com.killiangodet.recette.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.killiangodet.recette.role.RoleService;
import com.killiangodet.recette.user.model.request.UserDTO;
import com.killiangodet.recette.user.model.request.UserLoginDTO;
import com.killiangodet.recette.user.model.response.UserAdminResponseDTO;
import com.killiangodet.recette.user.model.response.UserLoginResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    private String bearerToken;

    @BeforeEach
    public void setUp() {
        this.bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnb2RldGtpbGxpYW5AeWFob28uY29tIiwiYXV0aCI6IlNVUEVSX0FETUlOIiwiZXhwIjoxNjg0Njk0MTAzfQ.-1TFwRNvb4j1qdR9NjMWp20-Apu-07HgVfr2pnwiXO4";

        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8"); // this is crucial
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

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

    @Test
    void testGetAllUsersWithParameters() throws Exception {
        UserAdminResponseDTO userAdminResponseDTO = new UserAdminResponseDTO(2, 1, 2, "User", "Test", "user_test", "user_test@gmail.com", LocalDate.of(1990,5,20), 2);

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

}

package com.killiangodet.recette.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8"); // this is crucial
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO("Mary Jane", "Watson", "MJWatson", "mjwatson@gmail.com", "superPassword123", LocalDate.of(1982, 4, 30), 2);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        UserDTO newUserDTO = objectMapper.readValue(contentAsString, UserDTO.class);
        assert newUserDTO.equals(userDTO);
    }

    @Test
    void testLoginUserWithoutJWT() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO("user_test@gmail.com", "passHash");
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO("TokenDeConnexion");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userLoginDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        UserLoginResponseDTO response = objectMapper.readValue(contentAsString, UserLoginResponseDTO.class);
        assert response.equals(userLoginResponseDTO);
    }

    @Test
    void testFailedLoginUserWithoutJWT() throws Exception {
        UserLoginDTO userLoginDTO = new UserLoginDTO("user_test@gmail.com", "passHash2");
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO("TokenDeConnexion");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userLoginDTO));
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isNotFound();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        UserLoginResponseDTO response = objectMapper.readValue(contentAsString, UserLoginResponseDTO.class);
        assert !response.equals(userLoginResponseDTO);
    }

    @Test
    void testGetAllUsersWithParameters() throws Exception {
        UserAdminResponseDTO userAdminResponseDTO = new UserAdminResponseDTO(1, 1, 2, "User", "Test", "user_test", "user_test@gmail.com", LocalDate.of(1990,5,20), 2);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user/all?nbResultPerPage=5&offset=0")
                .contentType("application/json");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        List<UserAdminResponseDTO> userAdminResponseDTOS = Arrays.asList(objectMapper.readValue(contentAsString, UserAdminResponseDTO[].class));
        assert userAdminResponseDTOS.contains(userAdminResponseDTO);
    }

}

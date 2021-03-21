package com.project.controller;

import com.project.entity.UserEntity;
import com.project.entity.UserRoleEntity;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should return correct http status for create account.")
    void shouldReturnCorrectHttpStatusForCreateAccount() throws Exception {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test");
        userEntity.setPassword("Test");
        userEntity.setRole(new UserRoleEntity("ROLE_USER"));
        userEntity.setId(1L);

        //and
        final String jsonRequestBody = "{\"username\": \"" + userEntity.getUsername() +
                "\", \"password\": \"" +
                userEntity.getPassword() +
                "\"}";

        //and
        when(userRepository.findByUsername(anyString()))
                .thenReturn(userEntity);
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        //and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "*/*");

        //when + then
        MvcResult mvcResult = mockMvc
                .perform(post("/api/user/createaccount")
                        .headers(httpHeaders)
                        .content((jsonRequestBody))
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
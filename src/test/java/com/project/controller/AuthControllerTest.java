package com.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.entity.UserEntity;
import com.project.entity.UserRoleEntity;
import com.project.model.JwtResponse;
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
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository repository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should return answer with token.")
    void shouldReturnAnswerWithToken() throws Exception {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Username");
        userEntity.setPassword("Password");
        userEntity.setRole(new UserRoleEntity("ROLE_USER"));
        userEntity.setId(1L);

        //and
        final String jsonRequestBody = "{\"username\": \"" + userEntity.getUsername() +
                "\", \"password\": \"" +
                userEntity.getPassword() +
                "\"}";

        //and
        when(repository.findByUsername(anyString()))
                .thenReturn(userEntity);
        when(repository.existsByUsername(anyString()))
                .thenReturn(true);
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        //and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "*/*");

        //when
        MvcResult mvcResult = mockMvc
                .perform(post("/api/auth/signin")
             .headers(httpHeaders)
        .content((jsonRequestBody))
                        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        //then
        JwtResponse jwt = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JwtResponse.class);
        assertThat(jwt).isNotNull();
        assertEquals(jwt.getUsername(), userEntity.getUsername());
        assertEquals( Arrays.asList("ROLE_USER"), jwt.getRoles());
        assertThat(jwt.getToken()).isNotNull();
    }

    @Test
    @DisplayName("Should return client error code 400 when receives wrong credentials.")
    void shouldReturnClientErrorCodeWhenReceivesWrongCredentials() throws Exception {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Username");
        userEntity.setPassword("Password");
        userEntity.setRole(new UserRoleEntity("ROLE_USER"));
        userEntity.setId(1L);

        //and
        final String jsonRequestBody = "{\"username\": \"WrongData\", \"password\": \"WrongData\"}";

        //and
        when(repository.findByUsername(anyString()))
                .thenReturn(userEntity);
        when(repository.existsByUsername(anyString()))
                .thenReturn(false);
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(true);

        //and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "*/*");

        //when + then
        mockMvc
                .perform(post("/api/auth/signin")
                        .headers(httpHeaders)
                        .content((jsonRequestBody))
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
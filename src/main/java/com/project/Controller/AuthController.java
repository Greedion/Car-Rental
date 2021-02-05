package com.project.controller;

import com.project.model.User;
import com.project.security.jwtauth.JwtUtils;
import com.project.service.authservice.AuthServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService,
                          AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @ApiOperation(value = "Log in.", notes = "Default account's : Admin/Admin User/User")
    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody User user, BindingResult result) {
        if(user ==null){
            logger.error("Attempt sing in with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        }else if (result.hasErrors()) {
            logger.error("Attempt to sing in with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return authService.start(user, authenticationManager);
    }

    private Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}

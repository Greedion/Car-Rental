package com.project.controller;

import com.project.model.JwtResponse;
import com.project.model.User;
import com.project.security.jwtauth.JwtUtils;
import com.project.service.authservice.AuthServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthenticationManager authenticationManager;
    final JwtUtils jwtUtils;
    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService,
                          AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @ApiOperation(value = "Log in.", notes = "Default account's : Admin/Admin User/User")
    @PostMapping(value = "/signin", consumes = "application/json", produces = "application/json")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody User user) {
        return authService.start(user, authenticationManager);
    }
}

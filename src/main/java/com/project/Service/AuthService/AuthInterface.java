package com.project.service.authservice;

import com.project.model.JwtResponse;
import com.project.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthInterface {

    ResponseEntity<JwtResponse> start(User user, AuthenticationManager authenticationManager);

}

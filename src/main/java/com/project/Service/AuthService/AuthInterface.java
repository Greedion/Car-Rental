package com.project.Service.AuthService;

import com.project.POJO.POJOUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

public interface AuthInterface {

    ResponseEntity<?> start(POJOUser pojoUser, AuthenticationManager authenticationManager);

}

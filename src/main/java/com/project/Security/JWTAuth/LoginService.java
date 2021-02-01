package com.project.Security.JWTAuth;

import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;

public interface LoginService {

    ResponseEntity<?> refreshSession(String refreshToken, String inputUsername) throws ServletException;

    ResponseEntity<?> start(POJOUser pojoUser);
}

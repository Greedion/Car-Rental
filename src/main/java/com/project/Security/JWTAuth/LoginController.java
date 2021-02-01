package com.project.Security.JWTAuth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    private final LoginServiceImpl loginServiceImpl;

    public LoginController(LoginServiceImpl loginServiceImpl) {
        this.loginServiceImpl = loginServiceImpl;
    }

    @PostMapping(value = "/logIn")
    ResponseEntity<?> login(@RequestBody POJOUser pojoUser) {
        return loginServiceImpl.start(pojoUser);
    }

    @PostMapping(value = "/refresh")
    ResponseEntity<?> refreshToken(@RequestParam String refreshToken, HttpServletRequest httpServletRequest) throws ServletException {
        String username = (String) httpServletRequest.getAttribute("username");
        return loginServiceImpl.refreshSession(refreshToken, username);
    }

}

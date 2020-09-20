package com.project.demo.Seciurity.JWTAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/logIn")
    ResponseEntity<?> login(@RequestBody POJOUser pojoUser) {
        return loginService.start(pojoUser);
    }

    @PostMapping(value = "/refresh")
    ResponseEntity<?> refreshToken(@RequestParam String refreshToken, HttpServletRequest httpServletRequest) throws ServletException {
        String username = (String) httpServletRequest.getAttribute("username");
        return loginService.refreshSession(refreshToken, username);
    }


}

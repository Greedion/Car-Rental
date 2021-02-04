package com.project.Controller;

import com.project.Security.JWTAuth.JwtUtils;
import com.project.Service.AuthService.AuthServiceImpl;
import com.project.POJO.POJOUser;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    ResponseEntity<?> login(@RequestBody POJOUser pojoUser) {
        if(pojoUser==null){
            logger.error("Attempt login with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt login with empty input data");
        }
        return authService.start(pojoUser, authenticationManager);
    }


}

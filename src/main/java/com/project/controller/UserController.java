package com.project.controller;

import com.project.exception.ExceptionsMessageArchive;
import com.project.model.FullUser;
import com.project.model.Money;
import com.project.service.userservice.UserServiceImpl;
import com.project.model.User;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Transfer money to account.", notes = "Needed authentication")
    @PostMapping(value = "/moneytransfer", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> moneyTransfer(@Valid @RequestBody Money money, HttpServletRequest httpServletRequest) {
            String username = (String) httpServletRequest.getAttribute("username");
        if (username == null) {
            logger.error(ExceptionsMessageArchive.USER_C_NULL_USERNAME_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionsMessageArchive.USER_C_NULL_USERNAME_EXCEPTION);
        }
            return userService.topUpAccount(username, money.getMoney());
    }

    @ApiOperation(value = "Get all users.", notes = "Needed authorization from Admin account")
    @GetMapping(produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FullUser>> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Create account.")
    @PreAuthorize("hasRole('permitAll()')")
    @PostMapping(value = "/createaccount", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createAccount(@Valid @RequestBody User user) {
                return userService.createAccount(user);
    }
}

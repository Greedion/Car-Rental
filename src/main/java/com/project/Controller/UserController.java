package com.project.Controller;

import com.project.DataTransferObject.UserDTO;
import com.project.Exception.ServiceOperationException;
import com.project.Service.UserService.UserServiceImpl;
import com.project.POJO.POJOUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/moneytransfer", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> moneyTransfer(String inputMoney, HttpServletRequest httpServletRequest) throws ServiceOperationException {
        if(inputMoney == null){
            logger.error("Attempt did money transfer with empty inputMoney value.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt did money transfer with empty inputMoney value.");
        }else {
            String username = (String) httpServletRequest.getAttribute("username");
            if (username == null) {
                logger.error("Attempt to transfer money with outdated login token. Or user doesn't exist.");
                return ResponseEntity.badRequest().build();
            }
            return userService.topUpAccount(username, inputMoney);
        }
    }

    @GetMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserDTO>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/createaccount", produces = "application/json", consumes = "application/json")
    ResponseEntity<?> createAccount(@Valid @RequestBody POJOUser pojoUser, BindingResult result) {

        if (pojoUser == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else if (pojoUser.getUsername() == null || pojoUser.getPassword() == null) {
            logger.error("Attempt create account with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt create account with empty input data.");
        } else {
            if(result.hasErrors()) {
                logger.error("Attempt to create account with wrong data structure.");
                return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
            }else{
                return userService.createAccount(pojoUser);
            }
        }
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

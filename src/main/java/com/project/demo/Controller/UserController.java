package com.project.demo.Controller;
import com.project.demo.Security.JWTAuth.POJOUser;
import com.project.demo.Service.UserService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = "moneyTransfer")
    ResponseEntity<?> moneyTransfer(String inputMoney, HttpServletRequest httpServletRequest) throws ServletException {
        String username = (String) httpServletRequest.getAttribute("username");
        return userService.topUpAccount(username, inputMoney);
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "createAccount")
    ResponseEntity<?> createAccount(@Valid @RequestBody POJOUser pojoUser, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return userService.createAccount(pojoUser);
    }
}

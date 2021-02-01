package com.project.Controller;
import com.project.Exception.ServiceOperationException;
import com.project.Service.UserService.UserServiceImpl;
import com.project.Security.JWTAuth.POJOUser;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping(value = "moneyTransfer")
    ResponseEntity<?> moneyTransfer(String inputMoney, HttpServletRequest httpServletRequest) throws ServiceOperationException {
        String username = (String) httpServletRequest.getAttribute("username");
        return userService.topUpAccount(username, inputMoney);
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "createAccount")
    ResponseEntity<?> createAccount(@Valid @RequestBody POJOUser pojoUser, BindingResult result) {
        ResponseEntity<?> errorMap = RentalController.validObject(result);
        if (errorMap != null) return errorMap;
        return userService.createAccount(pojoUser);
    }
}

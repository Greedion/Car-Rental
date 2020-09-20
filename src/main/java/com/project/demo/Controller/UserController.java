package com.project.demo.Controller;
import com.project.demo.Service.UserService.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
}

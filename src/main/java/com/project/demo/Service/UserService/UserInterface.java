package com.project.demo.Service.UserService;
import com.project.demo.Seciurity.JWTAuth.POJOUser;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;

public interface UserInterface {
    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException;

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> createAccount(POJOUser inputUser);
}

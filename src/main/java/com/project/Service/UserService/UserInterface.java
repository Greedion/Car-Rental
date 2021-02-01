package com.project.Service.UserService;
import com.project.Exception.ServiceOperationException;
import com.project.Security.JWTAuth.POJOUser;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;

public interface UserInterface {

    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException, ServiceOperationException;

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> createAccount(POJOUser inputUser);
}

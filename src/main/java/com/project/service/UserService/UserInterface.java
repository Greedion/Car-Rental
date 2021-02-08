package com.project.service.userservice;

import com.project.model.FullUser;
import com.project.exception.ServiceOperationException;
import com.project.model.User;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;
import java.util.List;

public interface UserInterface {

    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException, ServiceOperationException;

    ResponseEntity<List<FullUser>> getAllUsers();

    ResponseEntity<?> createAccount(User inputUser);
}

package com.project.service.userservice;

import com.project.model.FullUser;
import com.project.model.User;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface UserInterface {

    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue);

    ResponseEntity<List<FullUser>> getAllUsers();

    ResponseEntity<?> createAccount(User inputUser);
}

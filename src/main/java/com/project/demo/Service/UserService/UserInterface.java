package com.project.demo.Service.UserService;

import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;

public interface UserInterface {
    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException;
    ResponseEntity<?> getAllUsers();
}

package com.project.Service.UserService;
import com.project.DataTransferObject.UserDTO;
import com.project.Exception.ServiceOperationException;
import com.project.POJO.POJOUser;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;
import java.util.List;

public interface UserInterface {

    ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException, ServiceOperationException;

    ResponseEntity<List<UserDTO>> getAllUsers();

    ResponseEntity<?> createAccount(POJOUser inputUser);
}

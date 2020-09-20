package com.project.demo.Service.UserService;

import com.project.demo.DataTransferObject.UserDTA;
import com.project.demo.Entity.UserEntity;
import com.project.demo.Respository.UserRepository;
import com.project.demo.Utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {

    UserRepository userRepository;
    private final String  FORMAT_EXCEPTION = "Money parse format exception";
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


     public ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException {
        if(userRepository.existsByUsername(inputUsername)){
            UserEntity account = userRepository.findByUsername(inputUsername);
            try {
                account.setMoneyOnTheAccount(account.getMoneyOnTheAccount().add(new BigDecimal(inputMoneyValue)));
            }catch (NumberFormatException e){
                throw new ServletException(FORMAT_EXCEPTION);
            }
            userRepository.save(account);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
     }

     public ResponseEntity<?> getAllUsers(){
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserDTA> returnObject = new ArrayList<>();
         for (UserEntity x: allUserEntities
              ) {
             returnObject.add(UserMapper.mapperFormUserEntityToUserDTA(x));
         }
         return ResponseEntity.ok(returnObject);
     }



}

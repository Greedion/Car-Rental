package com.project.Service.UserService;

import com.project.DataTransferObject.UserDTO;
import com.project.Entity.UserEntity;
import com.project.Entity.UserRoleEntity;
import com.project.Exception.ServiceOperationException;
import com.project.Repository.UserRepository;
import com.project.Repository.UserRoleRepository;
import com.project.POJO.POJOUser;
import com.project.Utils.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserInterface {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String FORMAT_EXCEPTION = "Money parse format exception";
    private final static String DEFAULT_USER_ROLE = "ROLE_USER";

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServiceOperationException {
        if (userRepository.existsByUsername(inputUsername)) {
            UserEntity account = userRepository.findByUsername(inputUsername);
            try {
                account.setMoneyOnTheAccount(account.getMoneyOnTheAccount().add(new BigDecimal(inputMoneyValue)));
            } catch (NumberFormatException e) {
                throw new ServiceOperationException(FORMAT_EXCEPTION);
            }
            userRepository.save(account);
            return ResponseEntity.ok().build();
        } else {
            logger.error("Account with this username doesn't exist");
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserDTO> returnObject = new ArrayList<>();
        for (UserEntity x : allUserEntities) {
            returnObject.add(UserMapper.mapperFormUserEntityToUserDTA(x));
        }
        return ResponseEntity.ok(returnObject);
    }

    public ResponseEntity<?> createAccount(POJOUser inputUser) {
        Optional<UserRoleEntity> userRole = Optional.ofNullable(userRoleRepository.findByRole(DEFAULT_USER_ROLE));
        if (userRole.isPresent() && !userRepository.existsByUsername(inputUser.getUsername())) {
            UserEntity user = new UserEntity(inputUser.getUsername(), passwordEncoder.encode(inputUser.getPassword()), userRole.get(), BigDecimal.valueOf(0L));
            userRepository.save(user);
            return ResponseEntity.ok().build();
        } else{
            logger.error("Received wrong DEFAULT_USER_ROLE or account with this username doesn't exist");
            return ResponseEntity.badRequest().build();}
    }
}

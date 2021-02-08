package com.project.service.userservice;

import com.project.model.FullUser;
import com.project.entity.UserEntity;
import com.project.entity.UserRoleEntity;
import com.project.exception.ServiceOperationException;
import com.project.repository.UserRepository;
import com.project.repository.UserRoleRepository;
import com.project.model.User;
import com.project.utils.UserMapper;
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

    public ResponseEntity<List<FullUser>> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<FullUser> returnObject = new ArrayList<>();
        for (UserEntity x : allUserEntities) {
            returnObject.add(UserMapper.mapperFormUserEntityToUserDTA(x));
        }
        return ResponseEntity.ok(returnObject);
    }

    public ResponseEntity<?> createAccount(User inputUser) {
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

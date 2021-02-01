package com.project.demo.Service.UserService;
import com.project.demo.DataTransferObject.UserDTO;
import com.project.demo.Entity.UserEntity;
import com.project.demo.Entity.UserRoleEntity;
import com.project.demo.Repository.UserRepository;
import com.project.demo.Repository.UserRoleRepository;
import com.project.demo.Security.JWTAuth.POJOUser;
import com.project.demo.Utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserInterface {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;
    PasswordEncoder passwordEncoder;

    private final String FORMAT_EXCEPTION = "Money parse format exception";
    private final String DEFAULT_USER_ROLE = "ROLE_USER";

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<?> topUpAccount(String inputUsername, String inputMoneyValue) throws ServletException {
        if (userRepository.existsByUsername(inputUsername)) {
            UserEntity account = userRepository.findByUsername(inputUsername);
            try {
                account.setMoneyOnTheAccount(account.getMoneyOnTheAccount().add(new BigDecimal(inputMoneyValue)));
            } catch (NumberFormatException e) {
                throw new ServletException(FORMAT_EXCEPTION);
            }
            userRepository.save(account);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        List<UserDTO> returnObject = new ArrayList<>();
        for (UserEntity x : allUserEntities
        ) {
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
        } else return ResponseEntity.badRequest().build();
    }


}

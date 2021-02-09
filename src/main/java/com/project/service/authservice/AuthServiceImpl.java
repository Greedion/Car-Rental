package com.project.service.authservice;

import com.project.entity.UserEntity;
import com.project.exception.ExceptionsMessageArchive;
import com.project.model.JwtResponse;
import com.project.repository.UserRepository;
import com.project.model.User;
import com.project.security.jwtauth.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthInterface {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    private boolean checkAccountExist(User user) {
        return userRepository.existsByUsername(user.getUsername());
    }

    private UserEntity returnAccount(User user) {
        return userRepository.findByUsername(user.getUsername());
    }

    private boolean checkCredentials(User user) {
        UserEntity account = returnAccount(user);
        return passwordEncoder.matches(user.getPassword(), account.getPassword());
    }

    @Override
    public ResponseEntity<JwtResponse> start(User user, AuthenticationManager authenticationManager) {
        if (checkAccountExist(user) && checkCredentials(user)) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserEntity userDetails = (UserEntity) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles));
        }

        logger.error(ExceptionsMessageArchive.AUTH_S_INCORRECT_DATA_EXCEPTION);
        return ResponseEntity.badRequest().build();
    }
}

package com.project.Service.AuthService;

import com.project.Entity.UserEntity;
import com.project.Payload.JwtResponse;
import com.project.Repository.UserRepository;
import com.project.POJO.POJOUser;
import com.project.Security.JWTAuth.JwtUtils;
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

    private Boolean checkAccountExist(POJOUser pojoUser) {
        return userRepository.existsByUsername(pojoUser.getUsername());
    }

    private UserEntity returnAccount(POJOUser pojoUser) {
        return userRepository.findByUsername(pojoUser.getUsername());
    }

    private Boolean checkCredentials(POJOUser pojoUser) {
        UserEntity account = returnAccount(pojoUser);
        return passwordEncoder.matches(pojoUser.getPassword(), account.getPassword());
    }

    @Override
    public ResponseEntity<?> start(POJOUser pojoUser, AuthenticationManager authenticationManager) {
        if (checkAccountExist(pojoUser)) {
            if (checkCredentials(pojoUser)) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(pojoUser.getUsername(), pojoUser.getPassword()));
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
        }
        logger.error("Received incorrect logging data.");
        return ResponseEntity.badRequest().build();
    }
}

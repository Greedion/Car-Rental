package com.project.demo.Security.JWTAuth;

import com.project.demo.Entity.UserEntity;
import com.project.demo.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.*;

@Service
public class LoginService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Value("${jwt.secret_key}")
    private  String SECRET_KEY;

    @Value("${jwt.login_token_expiration_time}")
    private  long expirationTime;

    @Value("${jwt.refresh_token_expiration_time}")
    private  long EXPIRATION_TIME_FOR_REFRESH_TOKEN;

    private final String REFRESH_TOKEN_SPECIAL_CODE = "773095929936149";

    private final String REFRESH_TOKEN_EXCEPTION = "Wrong refresh token Structure or Token Expired";

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private Boolean checkAccountExist(POJOUser pojoUser) {
        return userRepository.existsByUsername(pojoUser.getUsername());
    }

    private UserEntity returnAccount(POJOUser pojoUser) {
        return userRepository.findByUsername(pojoUser.getUsername());
    }

    private Boolean checkCredentials(POJOUser pojoUser) {
        UserEntity account = returnAccount(pojoUser);

        if (passwordEncoder.matches(pojoUser.getPassword(), account.getPassword())) {
            return true;
        } else
            return false;
    }

    private String createToken(POJOUser pojoUser) {
        UserEntity account = returnAccount(pojoUser);
        long actualTime = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().getRole());
        claims.put("username", pojoUser.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(actualTime))
                .setExpiration(new Date(actualTime + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();
    }


    private String createRefreshToken(POJOUser pojoUser) {
        UserEntity account = returnAccount(pojoUser);
        long actualTime = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", account.getRole().getRole());
        claims.put("username", pojoUser.getUsername());
        claims.put("type", REFRESH_TOKEN_SPECIAL_CODE);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(actualTime))
                .setExpiration(new Date(actualTime + EXPIRATION_TIME_FOR_REFRESH_TOKEN))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .compact();

    }

    ResponseEntity<?> refreshSession(String refreshToken, String inputUsername) throws ServletException {
        if (refreshToken != null) {

            if (refreshToken.contains("\"Bearer ")) {
                int length = refreshToken.length();
                refreshToken = refreshToken.substring(1, length - 1);
            } else if (refreshToken.contains("Bearer ")) {
                refreshToken = refreshToken.substring(7);
            }
            refreshToken = refreshToken.replace("Bearer ", "");

            try {
                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(refreshToken);
                String username = claimsJws.getBody().get("username").toString();
                String role = claimsJws.getBody().get("role").toString();
                String specialCode = claimsJws.getBody().get("type").toString();
                if (inputUsername.equals(username) && userRepository.existsByUsername(username) && specialCode.equals(REFRESH_TOKEN_SPECIAL_CODE)) {
                    UserEntity userAccount = userRepository.findByUsername(username);
                    if (userAccount.getRole().getRole().equals(role)) {
                        POJOUser objectForCreateLoginToken = new POJOUser(inputUsername, null);
                        return ResponseEntity.ok(createToken(objectForCreateLoginToken));
                    }

                }
            } catch (Exception e) {
                throw new ServletException(REFRESH_TOKEN_EXCEPTION);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    ResponseEntity<?> start(POJOUser pojoUser) {
        if (checkAccountExist(pojoUser)) {
            if (checkCredentials(pojoUser)) {
                return ResponseEntity.ok(createToken(pojoUser) + "," + createRefreshToken(pojoUser));
            }
        }
        return ResponseEntity.badRequest().build();
    }


}

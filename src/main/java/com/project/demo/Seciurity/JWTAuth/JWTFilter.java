package com.project.demo.Seciurity.JWTAuth;

import com.project.demo.Respository.UserRepository;
import com.project.demo.Respository.UserRoleRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JWTFilter extends BasicAuthenticationFilter {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;

    public JWTFilter(AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        List<String> exceptionEndPoints = new ArrayList<>();
        exceptionEndPoints.add("/logIn");


        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }

        if (checkException(exceptionEndPoints, request)) {
            addCorsHeader(response);
            chain.doFilter(request, response);
            return;
        }

        addCorsHeader(response);
        String header = request.getHeader("Authorization");
        if (header != null) {

            if (header.contains("\"Bearer ")) {
                int length = header.length();
                header = header.substring(1, length - 1);
            } else if (header.contains("Bearer ")) {
                header = header.substring(7);
            }
        } else {
            throw new ServletException("Token is empty");
        }
        header = header.replace("Bearer ", "");
        UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("mysecret".getBytes()).parseClaimsJws(header);
        String username = claimsJws.getBody().get("username").toString();
        String role = claimsJws.getBody().get("role").toString();
        if (verification(username, role)) {
            SecurityContextHolder.getContext().setAuthentication(authResult);
            request.setAttribute("username", username);
            chain.doFilter(request, response);
        } else {
            throw new ServletException("Verification failed");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) throws ServletException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("mysecret".getBytes()).parseClaimsJws(header);
            String username = claimsJws.getBody().get("username").toString();
            String role = claimsJws.getBody().get("role").toString();
            System.out.println(role);
            Set<SimpleGrantedAuthority> simpleGrantedAuthorites = Collections.singleton(new SimpleGrantedAuthority(role));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorites);
            return usernamePasswordAuthenticationToken;
        } catch (Exception e) {
            throw new ServletException("Wrong Token Structure or Token Expired");
        }
    }

    private void addCorsHeader(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }

    private boolean verification(String username, String role) {
        if (userRepository.existsByUsername(username))
            if (userRoleRepository.existsByRole(role)) {
                return userRepository.existsByUsernameAndRole(username, userRoleRepository.findByRole(role));
            }
        return false;
    }

    boolean checkException(List<String> exceptionEndPoints, HttpServletRequest request) {
        for (String x : exceptionEndPoints
        ) {
            if (request.getRequestURI().contains(x)) {
                return true;
            }
        }
        return false;
    }
}

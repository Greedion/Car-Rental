package com.project.Security;

import com.project.Entity.UserEntity;
import com.project.Entity.UserRoleEntity;
import com.project.Security.JWTAuth.JWTFilter;
import com.project.Repository.UserRepository;
import com.project.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    public WebSecurityConfig(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/brand/add").hasRole("ADMIN")
                .antMatchers("/api/brand/update").hasRole("ADMIN")
                .antMatchers("/api/brand/delete").hasRole("ADMIN")
                .antMatchers("/api/car/add").hasRole("ADMIN")
                .antMatchers("/api/car/update").hasRole("ADMIN")
                .antMatchers("/api/car/delete").hasRole("ADMIN")
                .antMatchers("/api/user/getAll").hasRole("ADMIN")
                .antMatchers("/api/user/moneyTransfer").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/loan/createReservation").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/brand/getOne").permitAll()
                .antMatchers("/api/car/getOne").permitAll()
                .antMatchers("/api/brand/getAll").permitAll()
                .antMatchers("/api/car/getAll").permitAll()
                .antMatchers("/api/logIn").permitAll()
                .antMatchers("/api/loan/getAll").permitAll()
                .antMatchers("/api/user/createAccount").permitAll()
                .and()
                .addFilter(new JWTFilter(authenticationManager(), userRepository, userRoleRepository, SECRET_KEY));

        http.csrf().disable();
        http.cors().disable();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createSampleUser() {
        Optional<UserRoleEntity> userRole = Optional.ofNullable(userRoleRepository.findByRole("ROLE_USER"));
        Optional<UserRoleEntity> adminRole = Optional.ofNullable(userRoleRepository.findByRole("ROLE_ADMIN"));
        if (userRole.isPresent() && adminRole.isPresent()) {
            UserEntity user = new UserEntity("User", passwordEncoder().encode("User"), userRole.get(), BigDecimal.valueOf(0L));
            UserEntity admin = new UserEntity("Admin", passwordEncoder().encode("Admin"), adminRole.get(), BigDecimal.valueOf(0L));
            userRepository.save(user);
            userRepository.save(admin);
        }
    }
}

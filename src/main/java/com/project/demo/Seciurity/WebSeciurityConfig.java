package com.project.demo.Seciurity;

import com.project.demo.Entity.UserEntity;
import com.project.demo.Entity.UserRoleEntity;
import com.project.demo.Respository.UserRepository;
import com.project.demo.Respository.UserRoleRepository;
import com.project.demo.Seciurity.JWTAuth.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class WebSeciurityConfig extends WebSecurityConfigurerAdapter {

    UserRepository userRepository;
    UserRoleRepository userRoleRepository;

    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    @Autowired
    public WebSeciurityConfig(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/brand/add").hasRole("ADMIN")
                .antMatchers("/brand/update").hasRole("ADMIN")
                .antMatchers("/brand/delete").hasRole("ADMIN")
                .antMatchers("/car/add").hasRole("ADMIN")
                .antMatchers("/car/update").hasRole("ADMIN")
                .antMatchers("/car/delete").hasRole("ADMIN")
                .antMatchers("/user/getAll").hasAnyRole("USER", "ADMIN")
                .antMatchers("/brand/getOne").hasAnyRole("USER", "ADMIN")
                .antMatchers("user/moneyTransfer").hasAnyRole("USER", "ADMIN")
                .antMatchers("/loan/createReservation").hasAnyRole("USER", "ADMIN")
                .antMatchers("/car/getOne").permitAll()
                .antMatchers("/brand/getAll").permitAll()
                .antMatchers("/car/getAll").permitAll()
                .antMatchers("/logIn").permitAll()
                .antMatchers("loan/getAll").permitAll()
                .antMatchers("user/createAccount").permitAll()
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

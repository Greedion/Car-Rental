package com.project.Security;

import com.project.Entity.UserEntity;
import com.project.Entity.UserRoleEntity;
import com.project.Security.JWTAuth.AuthEntryPointJwt;
import com.project.Security.JWTAuth.JWTFilter;
import com.project.Repository.UserRepository;
import com.project.Repository.UserRoleRepository;
import com.project.Security.JWTAuth.JwtUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserRepository userRepository,
                             UserRoleRepository userRoleRepository,
                             AuthEntryPointJwt unauthorizedHandler,
                             JwtUtils jwtUtils,
                             UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    JWTFilter authenticationJwtTokenFilter() {
        return new JWTFilter(jwtUtils, userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .cors()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "api/user/createaccount").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            http.csrf().disable();

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

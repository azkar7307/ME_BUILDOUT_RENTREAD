package com.crio.rent_read.config;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  
  private final UserDetailsServiceImpl userDetailsService;
    // Object userService;
  @Bean
  SecurityFilterChain sequrityFilterChain(HttpSecurity httpSecurity) throws Exception {
    // disable the CSRF to test from localhost and Postman.
    httpSecurity.csrf(csrf -> csrf.disable());

    httpSecurity.authenticationProvider(authenticationProvider());

    httpSecurity.authorizeHttpRequests(configurer -> configurer
      .requestMatchers( "auth/signup")
      .permitAll()
      .requestMatchers("auth/login")
      .permitAll()

      .requestMatchers("/books/**")
      .hasAnyRole("ADMIN")
      
      .anyRequest()
      .authenticated()
      );

      httpSecurity.httpBasic(Customizer.withDefaults());

      return httpSecurity.build();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return daoAuthenticationProvider;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
}
package com.crio.rent_read.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.dto.request.LogInRequest;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;
import com.crio.rent_read.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest userRegisterRequest) {
        log.info("New User requested for registeration");
        UserResponse userResponse = authService.registerUser(userRegisterRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginRequest(@RequestBody LogInRequest loginRequest) {
        log.info("Request received to login to account");
        UserResponse userResponse = authService.loginUser(loginRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }


}

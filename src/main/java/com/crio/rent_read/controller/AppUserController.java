package com.crio.rent_read.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;
import com.crio.rent_read.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        log.info("New User requested for registeration");
        UserResponse userResponse = appUserService.registerUser(registerRequest);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}

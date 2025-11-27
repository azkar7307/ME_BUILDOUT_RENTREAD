package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.dto.request.LogInRequest;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;
import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.service.AuthService;
import com.crio.rent_read.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  

    private final AppUserRepository userRepository;
    private final ValidationServiceImpl validationService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse registerUser(RegisterRequest registerRequset) {
        validationService.validateUserExistsByEmail(registerRequset.getEmail());
        AppUser newUser = modelMapper.map(registerRequset, AppUser.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        AppUser savedUser = userRepository.save(newUser);
        log.info(
            "User '{}' with email '{}' registered successfully.",
            savedUser.getId(),
            Util.mask(savedUser.getEmail())
        );
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse loginUser(LogInRequest request) {
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            request.getEmail(), 
            request.getPassword()
        );

        authenticationManager.authenticate(authentication);
        AppUser user = validationService.validateAndGetUserByEmail(request.getEmail());
        log.info("User '{}' login successfull", Util.mask(user.getEmail()));
        return modelMapper.map(user, UserResponse.class);

        // authenticationManager.authenticate(
        //     new UsernamePasswordAuthenticationToken(
        //         request.getEmail(), 
        //         request.getPassword()
        //     )
        // );                                      
    }

}
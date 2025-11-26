package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;
import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.service.AppUserService;
import com.crio.rent_read.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;
    private final ValidationServiceImpl validationService;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse registerUser(RegisterRequest registerRequset) {
        validationService.validateUserExistsByEmail(registerRequset.getEmail());
        AppUser newUser = modelMapper.map(registerRequset, AppUser.class);
        AppUser savedUser = userRepository.save(newUser);
        log.info(
                "User '{}' with email '{}' registered successfully.",
                savedUser.getId(),
                Util.mask(savedUser.getEmail())
        );
        return modelMapper.map(savedUser, UserResponse.class);
    }

}

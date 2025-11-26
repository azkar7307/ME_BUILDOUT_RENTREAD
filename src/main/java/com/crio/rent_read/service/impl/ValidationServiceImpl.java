package com.crio.rent_read.service.impl;

import lombok.RequiredArgsConstructor;
import com.crio.rent_read.exception.ConflictException;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.service.ValidationService;
import com.crio.rent_read.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    
    private final AppUserRepository userRepository;

    @Transactional(readOnly = true)
    public void validateUserExistsByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new ConflictException(Util.mask(email), "User");
        }
    }
}

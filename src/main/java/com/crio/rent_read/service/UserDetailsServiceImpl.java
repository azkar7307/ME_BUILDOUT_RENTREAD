package com.crio.rent_read.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    
    private final ValidationService validationService;
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return validationService.validateAndGetUserByEmail(username);      
    }
        
}

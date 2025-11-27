package com.crio.rent_read.service;

import com.crio.rent_read.dto.request.LogInRequest;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;

public interface AuthService {
    UserResponse registerUser(RegisterRequest registerRequset);  
    
    UserResponse loginUser(LogInRequest request);
}

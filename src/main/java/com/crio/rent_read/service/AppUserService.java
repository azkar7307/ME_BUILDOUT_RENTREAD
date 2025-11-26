package com.crio.rent_read.service;

import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;

public interface AppUserService {
    UserResponse registerUser(RegisterRequest sampleUser);

}

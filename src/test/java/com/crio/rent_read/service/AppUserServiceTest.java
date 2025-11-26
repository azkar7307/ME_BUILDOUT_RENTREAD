package com.crio.rent_read.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.crio.rent_read.dto.request.RegisterRequest;
import com.crio.rent_read.dto.response.UserResponse;
import com.crio.rent_read.entity.AppUser;
import com.crio.rent_read.entity.enums.Role;
import com.crio.rent_read.repository.AppUserRepository;
import com.crio.rent_read.service.impl.AppUserServiceImpl;
import com.crio.rent_read.service.impl.ValidationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class AppUserServiceTest {
    
    @Mock
    private AppUserRepository userRepository;

    @Mock
    private ValidationServiceImpl validationServiceImpl;

    
    @Spy
    private ModelMapper modelMapper = new ModelMapper();


    @InjectMocks
    private AppUserServiceImpl userServiceImpl;

    RegisterRequest userRegisterReq;
    AppUser sampleUser;

    @BeforeEach
    void setup() {
        userRegisterReq = new RegisterRequest(
            "John", 
            "Doe", 
            "johndoe123@crio.in", 
            "123456"
        );

        sampleUser = modelMapper.map(userRegisterReq, AppUser.class);
        sampleUser.setId(1L);
        sampleUser.setRole(Role.USER);
    }

    @Test
    void registerUser_Return_UserResponse() {

        // setup
        doNothing().when(validationServiceImpl).validateUserExistsByEmail(anyString());
        when(userRepository.save(any(AppUser.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        // execute
        UserResponse userResponse = userServiceImpl.registerUser(userRegisterReq);
        
        assertNotNull(userResponse);
        assertEquals(sampleUser.getFirstName(), userResponse.getFirstName());
        assertEquals(sampleUser.getEmail(), userResponse.getEmail());

        // verify
        // verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(AppUser.class));
        verify(modelMapper, times(1)).map(any(AppUser.class), eq(UserResponse.class));
    }

    // @Test
    // void registerUser_With_Existing_Email_Throw_ConflictException() {

    //     // setup
    //     when(userRepository.findByEmail(anyString())).thenReturn(sampleUser);
    //     when(userRepository.save(any(AppUser.class))).thenAnswer(
    //         invocation -> invocation.getArgument(0)
    //     );

    //     // execute
    //     UserResponse userResponse = userServiceImpl.registerUser(userRegisterReq);
        
    //     assertNotNull(userResponse);
    //     assertEquals(sampleUser.getFirstName(), userResponse.getFirstName());
    //     assertEquals(sampleUser.getEmail(), userResponse.getEmail());

    //     // verify
    //     verify(userRepository, times(1)).findByEmail(anyString());
    //     verify(userRepository, times(1)).save(any(AppUser.class));
    //     verify(modelMapper, times(1)).map(any(AppUser.class), eq(UserResponse.class));
    // }

}

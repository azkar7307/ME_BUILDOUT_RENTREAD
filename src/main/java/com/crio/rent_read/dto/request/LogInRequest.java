package com.crio.rent_read.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogInRequest {

    @NotBlank(message = "Email must not be empty or null")
    @Email(message = "Email must be valid")
    private String email;
    
    @NotBlank (message = "Password must not be empty")
    // @Size(min = 6, message = "Password must contains at least 6 character long")
    private String password;
    
}

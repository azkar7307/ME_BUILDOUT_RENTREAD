package com.crio.rent_read.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.crio.rent_read.entity.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}

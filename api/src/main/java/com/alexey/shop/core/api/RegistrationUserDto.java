package com.alexey.shop.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDto {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

}

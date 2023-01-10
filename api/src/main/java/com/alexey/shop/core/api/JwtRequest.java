package com.alexey.shop.core.api;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}

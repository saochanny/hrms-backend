package com.chanty.hrms.dto.authentication;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}

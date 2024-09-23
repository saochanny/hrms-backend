package com.chanty.hrms.dto.mailVerify;

import lombok.Data;

@Data
public class PasswordVerify {
    private String password;
    private String token;
}

package com.chanty.hrms.dto.mail_verify;

import lombok.Data;

@Data
public class PasswordVerify {
  private String password;
  private String token;
}

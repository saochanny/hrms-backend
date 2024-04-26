package com.chanty.hrms.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
  private String accessToken;
  private String refreshToken;
  private Long accessExpire;
  private Long refreshExpire;
  private String type;
}

package com.chanty.hrms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private String email;
  private String username;
  private String firstName;
  private String lastName;
  private Boolean isEnable;
  private String tempPath;
}

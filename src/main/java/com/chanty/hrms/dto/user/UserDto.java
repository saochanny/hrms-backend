package com.chanty.hrms.dto.user;

import com.chanty.hrms.model.setup.Role;
import java.util.Set;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
  private String email;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private Boolean isEnable;
}

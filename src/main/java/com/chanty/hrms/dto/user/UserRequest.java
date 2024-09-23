package com.chanty.hrms.dto.user;

import com.chanty.hrms.model.setup.Role;
import java.util.Set;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String email;
  private String username;
  private String password;
  private String firstName;
  private String lastName;
  private Boolean isEnable;
}

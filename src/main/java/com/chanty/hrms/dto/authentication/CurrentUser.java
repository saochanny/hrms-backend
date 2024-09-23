package com.chanty.hrms.dto.authentication;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CurrentUser {
  private Long id;
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private Set<String> authorities;
  private String image;
}

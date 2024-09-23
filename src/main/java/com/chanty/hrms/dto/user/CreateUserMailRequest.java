package com.chanty.hrms.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserMailRequest {
    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}

package com.chanty.hrms.controller;

import com.chanty.hrms.dto.authentication.CurrentUser;
import com.chanty.hrms.dto.authentication.LoginRequest;
import com.chanty.hrms.dto.authentication.LoginResponse;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticationService.login(request));
  }

  @PostMapping("/token/refresh")
  public ResponseEntity<LoginResponse> refresh(
          @RequestParam(name = "refreshToken") String refreshToken) {
    return ResponseEntity.ok(authenticationService.renewToken(refreshToken));
  }

  @GetMapping("/token/valid")
  public ResponseEntity<Boolean> isValidate(@RequestParam("accessToken") String token) {
    return ResponseEntity.ok(authenticationService.isValidToken(token));
  }
  @GetMapping("/user")
  public ResponseEntity<CurrentUser> getUser(){
    return ResponseEntity.ok(authenticationService.getCurrentUser());
  }

  @PostMapping("/logout")
  public void logout(){
    authenticationService.logout();
  }
}

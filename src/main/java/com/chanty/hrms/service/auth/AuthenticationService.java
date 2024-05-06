package com.chanty.hrms.service.auth;

import com.chanty.hrms.dto.LoginRequest;
import com.chanty.hrms.dto.LoginResponse;
import com.chanty.hrms.exception.UnauthorizedException;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.UserRepository;
import com.chanty.hrms.service.UserService;
import com.chanty.hrms.service.impl.UserServiceImpl;
import com.chanty.hrms.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserService userService;

  @Value("${app.security.jwt.access_expires}")
  private int accessExpires;

  @Value("${app.security.jwt.refresh_expires}")
  private int refreshExpires;

  public LoginResponse login(LoginRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    if (authentication.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(authentication);
      AuthenticationUser user = (AuthenticationUser) authentication.getPrincipal();
      return loginResponse(user.getUsername());
    }
    throw new UnauthorizedException();
  }

  private LoginResponse loginResponse(String username) {
    String accessToken = jwtUtils.generateAccessToken(username);
    String refreshToken = jwtUtils.generateRefreshToken(username);
    return LoginResponse.builder()
        .accessExpire(jwtUtils.toExpiresMs(accessExpires))
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .refreshExpire(jwtUtils.toExpiresMs(refreshExpires))
        .type("Bearer ")
        .build();
  }

  public Boolean isValidToken(String token) {
    return jwtUtils.validateJwtToken(token);
  }

  public LoginResponse renewToken(String refreshToken) {
    if (Boolean.FALSE.equals(isValidToken(refreshToken))) {
      throw new UnauthorizedException("Refresh token is expire");
    }
    String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
    return loginResponse(username);
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    AuthenticationUser authenticationUser= (AuthenticationUser) principal;
      return userRepository.findByEmail(authenticationUser.getEmail()).orElseThrow(
              ()->new UnauthorizedException("Unauthorized user")
      );

  }


  public void logout() {
    SecurityContextHolder.clearContext();
  }
}

package com.chanty.hrms.service.auth;

import com.chanty.hrms.dto.authentication.CurrentUser;
import com.chanty.hrms.dto.authentication.LoginRequest;
import com.chanty.hrms.dto.authentication.LoginResponse;
import com.chanty.hrms.dto.user.UserResponse;
import com.chanty.hrms.exception.UnauthorizedException;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.UserRepository;
import com.chanty.hrms.service.impl.UserServiceImpl;
import com.chanty.hrms.utils.JwtUtils;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** Service class for handling user authentication operations. */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

  private final JwtUtils jwtUtils;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserServiceImpl userService;

  @Value("${app.security.jwt.access_expires}")
  private int accessExpires;

  @Value("${app.security.jwt.refresh_expires}")
  private int refreshExpires;

  /**
   * Authenticates a user based on the provided login credentials.
   *
   * @param request The login request containing user credentials.
   * @return A LoginResponse containing access and refresh tokens upon successful authentication.
   * @throws UnauthorizedException If authentication fails due to invalid credentials.
   */
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

  /**
   * Validates if a given token is a valid JWT token.
   *
   * @param token The JWT token to validate.
   * @return true if the token is valid, false otherwise.
   */
  public Boolean isValidToken(String token) {
    return jwtUtils.validateJwtToken(token);
  }

  /**
   * Generates a new access token using a valid refresh token.
   *
   * @param refreshToken The refresh token used to generate a new access token.
   * @return A LoginResponse containing a new access token and refresh token.
   * @throws UnauthorizedException If the refresh token is invalid or expired.
   */
  public LoginResponse renewToken(String refreshToken) {
    if (Boolean.FALSE.equals(isValidToken(refreshToken))) {
      throw new UnauthorizedException("Refresh token is expired");
    }
    String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
    return loginResponse(username);
  }

  /**
   * Retrieves the currently authenticated user.
   *
   * @return The User object representing the currently authenticated user.
   * @throws UnauthorizedException If the user is not authenticated or authorized.
   */
  public CurrentUser getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    AuthenticationUser authenticationUser = (AuthenticationUser) principal;
    User user =
        userRepository
            .findByEmail(authenticationUser.getEmail())
            .orElseThrow(() -> new UnauthorizedException("Unauthorized user"));
    return toCurrentUser(user);
  }

  /** Logs out the currently authenticated user by clearing the security context. */
  public void logout() {
    SecurityContextHolder.clearContext();
  }

  private CurrentUser toCurrentUser(User user) {
    UserResponse response = userService.toUserResponse(user);
    return CurrentUser.builder()
        .id(user.getId())
        .email(user.getEmail())
        .lastName(user.getLastName())
        .username(user.getUsername())
        .authorities(getUserAuthorities(user))
        .firstName(user.getFirstName())
        .image(response.getImage())
        .build();
  }

  private Set<String> getUserAuthorities(User user) {
    Set<String> roleAuthorities =
        user.getRoles().stream().map(role -> role.getName().getName()).collect(Collectors.toSet());

    Set<String> permissionAuthorities =
        user.getRoles().stream()
            .flatMap(
                role ->
                    role.getPermissions().stream().map(permission -> permission.getName().name()))
            .collect(Collectors.toSet());

    roleAuthorities.addAll(permissionAuthorities);

    return roleAuthorities;
  }
}

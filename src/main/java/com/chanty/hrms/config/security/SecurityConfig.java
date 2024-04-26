package com.chanty.hrms.config.security;

import static com.chanty.hrms.model.setup.RoleEnum.*;

import com.chanty.hrms.service.auth.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final UserDetailServiceImpl userDetailService;
  private final AuthEntryPoint entryPoint;
  private final PasswordEncoder passwordEncoder;

  private final AuthTokenFilter authTokenFilter;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exception ->
                exception
                    .authenticationEntryPoint(entryPoint)
                    .accessDeniedHandler(new CustomAccessDenied()))
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(
                        "/swagger-ui/index.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/api/v1/auth/login",
                        "/api/v1/auth/token/refresh")
                    .permitAll())
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers("/api/v1/roles/**")
                    .hasAnyRole(ADMIN.getName(), SUPER_ADMIN.getName(), HR.getName()))
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers("/api/v1/users/**")
                    .hasAnyRole(ADMIN.getName(), SUPER_ADMIN.getName(), HR.getName()));
    http.sessionManagement(
        management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}

package com.chanty.hrms.config.security;

import com.chanty.hrms.service.auth.UserDetailServiceImpl;
import com.chanty.hrms.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;
  private final UserDetailServiceImpl userDetailService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = parseJwt(request);
    try {
        if(jwt!=null && jwtUtils.validateJwtToken(jwt)) {
          String username = jwtUtils.getUserNameFromJwtToken(jwt);
          UserDetails userDetails = userDetailService.loadUserByUsername(username);
          if (userDetails != null) {
            Authentication authentication =
                    new PreAuthenticatedAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        }

        filterChain.doFilter(request, response);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

}

package com.chanty.hrms.utils;

import com.chanty.hrms.common.constant.ClaimConstant;
import com.chanty.hrms.common.constant.RoleConstant;
import com.chanty.hrms.exception.UnauthorizedException;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
  private final UserRepository userRepository;

  @Value("${app.security.jwt.secret}")
  private String jwtSecret;

  @Value("${app.security.jwt.access_expires}")
  private int accessExpire;

  @Value("${app.security.jwt.refresh_expires}")
  private int refreshExpires;

  public String generateAccessToken(String username) {
    User user =
        userRepository
            .findByEmailOrUsername(username, username)
            .orElseThrow(UnauthorizedException::new);
    Set<SimpleGrantedAuthority> authorities =
        user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(RoleConstant.ROLE_PREFIX + role.getName()))
            .collect(Collectors.toSet());

    Set<SimpleGrantedAuthority> permissionAuthorities =
        user.getRoles().stream()
            .flatMap(
                role -> role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName().name())))
            .collect(Collectors.toSet());
    authorities.addAll(permissionAuthorities);
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .claim(ClaimConstant.AUTHORITIES, authorities)
        .setIssuer("com.chanty.hrms")
        .setExpiration(new Date((new Date()).getTime() + toExpiresMs(accessExpire)))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String generateRefreshToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setIssuer("com.chanty.hrms")
        .setExpiration(new Date((new Date()).getTime() + toExpiresMs(refreshExpires)))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

  public Long toExpiresMs(int jwtExpired) {
    return (long) jwtExpired * 1000 * 60;
  }
}

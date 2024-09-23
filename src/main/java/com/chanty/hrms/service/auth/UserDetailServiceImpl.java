package com.chanty.hrms.service.auth;

import com.chanty.hrms.common.constant.RoleConstant;
import com.chanty.hrms.exception.UsernameNotFoundException;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmailOrUsername(username, username)
            .orElseThrow(() -> new UsernameNotFoundException("Username or email not found"));

    return AuthenticationUser.builder()
        .authorities(getAuthorities(user.getRoles()))
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  private Set<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
    Set<SimpleGrantedAuthority> roleAuthorities =
        roles.stream()
            .map(role -> new SimpleGrantedAuthority(RoleConstant.ROLE_PREFIX + role.getName()))
            .collect(Collectors.toSet());
    roleAuthorities.addAll(permissionAuthorities(roles));
    return roleAuthorities;
  }

  private Set<SimpleGrantedAuthority> permissionAuthorities(Set<Role> roles) {
    return roles.stream()
        .flatMap(
            role ->
                role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName().name())))
        .collect(Collectors.toSet());
  }
}

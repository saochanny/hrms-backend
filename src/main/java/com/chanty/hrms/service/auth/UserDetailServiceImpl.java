package com.chanty.hrms.service.auth;

import com.chanty.hrms.common.constant.RoleConstant;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.UserRepository;
import java.util.Collection;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByEmailOrUsername(username,username)
            .orElseThrow(() -> new UsernameNotFoundException("user name or email is not found"));

    return AuthenticationUser.builder()
        .authorities(getAuthorities(user.getRoles()))
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  private Collection<SimpleGrantedAuthority> getAuthorities(Set<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(RoleConstant.ROLE_PREFIX + role.getName()))
        .toList();
  }
}

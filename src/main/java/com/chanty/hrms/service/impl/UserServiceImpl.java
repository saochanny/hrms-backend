package com.chanty.hrms.service.impl;

import com.chanty.hrms.dto.user.UserDto;
import com.chanty.hrms.exception.ResourceNotFoundException;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.RoleEnum;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.RoleRepository;
import com.chanty.hrms.repository.setup.UserRepository;
import com.chanty.hrms.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper mapper;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User createUser(UserDto userDto) {
    User user =
        User.builder()
            .email(userDto.getEmail())
            .firstName(userDto.getFirstName())
            .lastName(userDto.getLastName())
            .username(userDto.getUsername())
            .isEnable(true)
            .password(passwordEncoder.encode(userDto.getPassword()))
            .roles(Set.of(getDefaultRole()))
            .build();
    log.info("======= User register :{}",user);
    return userRepository.save(user);
  }

  @Override
  public User getById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public List<User> getUserInRole(Integer roleId) {
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role", Long.valueOf(roleId)));
    return userRepository.findAllByRolesIs(Set.of(role));
  }

  @Override
  public User update(UserDto userDto, Long id) {
    User user = this.getById(id);
    User update = toUserUpdate(userDto, user);
    return userRepository.save(update);
  }

  private Role getDefaultRole() {
    return roleRepository.findByName(RoleEnum.DEFAULT);
  }

  private User toUserUpdate(UserDto update, User user) {
    user.setPassword(passwordEncoder.encode(update.getPassword()));
    user.setUsername(update.getUsername());
    user.setEmail(update.getEmail());
    user.setIsEnable(update.getIsEnable());
    user.setFirstName(update.getFirstName());
    user.setLastName(update.getLastName());
    return user;
  }
  @Override
  public User assignRole(Set<Integer> roleIds, Long id){
    User user = this.getById(id);
    Set<Role> roles = roleRepository.findByIdIsIn(roleIds);
    log.info("======= role that assign :{}", roles);
    user.setRoles(roles);
    return userRepository.save(user);
  }
}

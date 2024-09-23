package com.chanty.hrms.service.impl;

import com.chanty.hrms.dto.PaginationResponse;
import com.chanty.hrms.dto.io.UploadFileResponse;
import com.chanty.hrms.dto.user.CreateUserMailRequest;
import com.chanty.hrms.dto.user.UserFilter;
import com.chanty.hrms.dto.user.UserRequest;
import com.chanty.hrms.dto.user.UserResponse;
import com.chanty.hrms.exception.CommonException;
import com.chanty.hrms.exception.ResourceNotFoundException;
import com.chanty.hrms.mapper.UserMapper;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.common.constant.RoleEnum;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.RoleRepository;
import com.chanty.hrms.repository.setup.UserRepository;
import com.chanty.hrms.service.UserService;
import com.chanty.hrms.specification.UserSpecification;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper mapper;
  private final RoleRepository roleRepository;
  private final UserFileService userFileService;
  private final MailCreateUserHandler mailCreateUserHandler;
  private final UserMapper userMapper;

  @Transactional(rollbackOn = Exception.class)
  @Override
  public UserResponse createUser(UserRequest userRequest) {
    User user =
        User.builder()
            .email(userRequest.getEmail())
            .firstName(userRequest.getFirstName())
            .lastName(userRequest.getLastName())
            .username(userRequest.getUsername())
            .isEnable(true)
            .roles(Set.of(getDefaultRole()))
            .build();
    String moved = this.userFileService.moveFile(userRequest.getTempPath());
    user.setImage(moved);
    log.info("======= User register :{}", user);
    User save;
    try {
      save = userRepository.save(user);

      // apply asynchronous sent mail to use in process create user
      CompletableFuture.runAsync(
          () -> mailCreateUserHandler.send(mapper.map(userRequest, CreateUserMailRequest.class)));
      return toUserResponse(save);
    } catch (DataIntegrityViolationException e) {
      log.error("======= error on created user", e);
      userFileService.delete(moved);
      throw e;
    }
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", id));
  }

  @Override
  public UserResponse getById(Long id) {
    return toUserResponse(getUserById(id));
  }

  @Override
  public List<UserResponse> getAll() {
    return userRepository.findAll().stream().map(this::toUserResponse).toList();
  }

  @Override
  public List<UserResponse> getUserInRole(Integer roleId) {
    Role role =
        roleRepository
            .findById(roleId)
            .orElseThrow(() -> new ResourceNotFoundException("Role", Long.valueOf(roleId)));
    return userRepository.findAllByRolesIs(Set.of(role)).stream()
        .map(this::toUserResponse)
        .toList();
  }

  @Override
  public UserResponse update(UserRequest userRequest, Long id) {
    User user = this.getUserById(id);
    User update = toUserUpdate(userRequest, user);
    User saved = userRepository.save(update);
    return toUserResponse(saved);
  }

  @Override
  public UserResponse uploadProfile(MultipartFile file, Long id) throws IOException {
    User user = this.getUserById(id);
    String filePath = userFileService.upload(file).getFilePath();
    // set filePath to user
    user.setImage(filePath);

    return toUserResponse(userRepository.save(user));
  }

  private Role getDefaultRole() {
    return roleRepository.findByName(RoleEnum.DEFAULT);
  }

  private User toUserUpdate(UserRequest update, User user) {
    user.setUsername(update.getUsername());
    user.setEmail(update.getEmail());
    user.setIsEnable(update.getIsEnable());
    user.setFirstName(update.getFirstName());
    user.setLastName(update.getLastName());
    if (update.getTempPath() != null && !update.getTempPath().isEmpty()) {
      String movedFile = this.userFileService.moveFile(update.getTempPath());
      user.setImage(movedFile);
    }
    if (update.getTempPath() == null || update.getTempPath().isEmpty()) {
      user.setImage(null);
    }
    return user;
  }

  @Override
  public UserResponse assignRole(Set<Integer> roleIds, Long id) {
    User user = this.getUserById(id);
    Set<Role> roles = roleRepository.findByIdIsIn(roleIds);
    log.info("======= role that assign :{}", roles);
    user.setRoles(roles);
    return toUserResponse(userRepository.save(user));
  }

  public UserResponse toUserResponse(User user) {
    UserResponse userResponse = userMapper.toUserResponse(user);
    UploadFileResponse download = userFileService.download(user.getImage());
    if (download != null) {
      userResponse.setImage(download.getFile());
      userResponse.setFilePath(download.getFilePath());
    }
    return userResponse;
  }

  @Override
  public PaginationResponse getUsers(UserFilter filter) {
    Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());
    Specification<User> spec;
    spec = Specification.where(UserSpecification.getContent(filter.getSearch()));
    Page<User> userPage = userRepository.findAll(spec, pageable);
    List<UserResponse> content = userPage.getContent().stream().map(this::toUserResponse).toList();
    return PaginationResponse.builder()
        .pageSize(userPage.getSize())
        .totalElement((int) userPage.getTotalElements())
        .size(userPage.getNumberOfElements())
        .currentPage(userPage.getPageable().getPageNumber() + 1)
        .totalPage(userPage.getTotalPages())
        .content(content)
        .build();
  }

  @Override
  public void deleteById(Long id) {
    User user = getUserById(id);
    List<RoleEnum> roleEnums = user.getRoles().stream().map(Role::getName).toList();
    if (id == 1 || new HashSet<>(roleEnums).containsAll(List.of(RoleEnum.SUPER_ADMIN,RoleEnum.ADMIN))) {
      log.error("======= Can not delete this user :{}", user);
      throw new CommonException("You are not allowed to delete this user");
    }
    if (user.getImage() != null && !user.getImage().isEmpty()) {
      userFileService.delete(user.getImage());
    }
    userRepository.deleteById(id);
  }

  @Override
  public void deleteProfileById(Long id) {
    User user = getUserById(id);
    if (user.getImage() != null && !user.getImage().isEmpty()) {
      userFileService.delete(user.getImage());
    }
  }

  @Override
  public List<Role> getRolesByUserId(Long id) {
    log.info("======= getRolesByUserId :{}", id);
    return this.getUserById(id).getRoles().stream().toList();
  }
}

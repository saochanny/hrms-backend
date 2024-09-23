package com.chanty.hrms.controller;

import com.chanty.hrms.dto.PaginationResponse;
import com.chanty.hrms.dto.user.UserFilter;
import com.chanty.hrms.dto.user.UserRequest;
import com.chanty.hrms.dto.user.UserResponse;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SUPER_ADMIN')")
  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
    return ResponseEntity.ok(userService.createUser(userRequest));
  }

  @GetMapping
  public ResponseEntity<PaginationResponse> getAllUsers(@ModelAttribute UserFilter filter){
    return ResponseEntity.ok(userService.getUsers(filter));
  }

  @PostMapping("/profile")
  public ResponseEntity<UserResponse> uploadProfile(@RequestPart MultipartFile file, @RequestParam Long id) throws IOException {
    return ResponseEntity.ok(userService.uploadProfile(file, id));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping("/role/{roleId}")
  public ResponseEntity<List<UserResponse>> getUsersByRole(@PathVariable(name = "roleId") Integer roleId) {
    return ResponseEntity.ok(userService.getUserInRole(roleId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(
          @RequestBody UserRequest update, @PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.update(update, id));
  }

  @DeleteMapping("/{id}/profile")
  public void deleteProfileById(@PathVariable(name = "id") Long id) {
    userService.deleteProfileById(id);
  }
  @PutMapping("/{id}/assign-roles")
  public ResponseEntity<UserResponse> assignRoles(
      @RequestBody Set<Integer> roleIds, @PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.assignRole(roleIds, id));
  }

  @DeleteMapping("/{id}")
  public void deleteUserById(@PathVariable(name = "id") Long id) {
    userService.deleteById(id);
  }

  @GetMapping("/{userId}/roles")
  public List<Role> getRolesFromUser(@PathVariable(name = "userId") Long userId){
    return userService.getRolesByUserId(userId);
  }
}

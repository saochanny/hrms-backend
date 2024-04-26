package com.chanty.hrms.controller;

import com.chanty.hrms.dto.user.UserDto;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok(userService.createUser(userDto));
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUser() {
    return ResponseEntity.ok(userService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping("/role/{roleId}")
  public ResponseEntity<List<User>> getUsersByRole(@PathVariable(name = "roleId") Integer roleId) {
    return ResponseEntity.ok(userService.getUserInRole(roleId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(
      @RequestBody UserDto update, @PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.update(update, id));
  }

  @PutMapping("/{id}/assign")
  public ResponseEntity<User> assignRoles(
      @RequestBody Set<Integer> roleIds, @PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(userService.assignRole(roleIds, id));
  }
}

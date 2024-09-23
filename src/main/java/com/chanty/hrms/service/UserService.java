package com.chanty.hrms.service;

import com.chanty.hrms.dto.PaginationResponse;
import com.chanty.hrms.dto.user.UserFilter;
import com.chanty.hrms.dto.user.UserRequest;
import com.chanty.hrms.dto.user.UserResponse;
import com.chanty.hrms.model.setup.Role;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getById(Long id);
    List<UserResponse> getAll();
    List<UserResponse> getUserInRole(Integer roleId);
    UserResponse update(UserRequest userRequest, Long id);
    UserResponse uploadProfile(MultipartFile file, Long id) throws IOException;
    UserResponse assignRole(Set<Integer> roleIds, Long id);
    PaginationResponse getUsers(UserFilter filter);
    void deleteById(Long id);
    void deleteProfileById(Long id);
    List<Role> getRolesByUserId(Long id);
}

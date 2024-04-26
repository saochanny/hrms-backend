package com.chanty.hrms.service;

import com.chanty.hrms.dto.user.UserDto;
import com.chanty.hrms.model.setup.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User createUser(UserDto userDto);
    User getById(Long id);
    List<User> getAll();
    List<User> getUserInRole(Integer roleId);
    User update(UserDto userDto , Long id);

    User assignRole(Set<Integer> roleIds, Long id);
}

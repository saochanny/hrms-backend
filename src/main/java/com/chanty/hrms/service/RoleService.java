package com.chanty.hrms.service;

import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.model.setup.Role;

import java.util.List;

public interface RoleService {
    Role createRole(RoleDto roleDto);
    Role getById(Integer id);
    List<Role> getAll();

    List<Role> saveAsList(List<RoleDto> roleDtos);

}

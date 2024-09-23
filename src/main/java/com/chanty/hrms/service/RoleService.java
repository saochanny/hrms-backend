package com.chanty.hrms.service;

import com.chanty.hrms.common.constant.PermissionEnum;
import com.chanty.hrms.common.constant.RoleEnum;
import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.model.setup.Permission;
import com.chanty.hrms.model.setup.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    Role createRole(RoleDto roleDto);
    Role getById(Integer id);
    List<Role> getAll();
    List<Role> filterRole(RoleEnum search);
    List<Role> saveAsList(List<RoleDto> roleDtos);
    void assignPermission(String name, Set<PermissionEnum> permissions);
    Role getByName(String name);
    List<Permission> getPermissionFromRole(Long roleId);
}

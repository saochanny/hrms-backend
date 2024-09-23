package com.chanty.hrms.service.impl;

import com.chanty.hrms.common.constant.PermissionEnum;
import com.chanty.hrms.common.constant.RoleEnum;
import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.exception.ResourceNotFoundException;
import com.chanty.hrms.model.setup.Permission;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.repository.setup.PermissionRepository;
import com.chanty.hrms.repository.setup.RoleRepository;
import com.chanty.hrms.service.RoleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
  private final ModelMapper mapper;
  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  @Override
  public Role createRole(RoleDto roleDto) {
    log.info("===== create role =====");
    return roleRepository.save(mapper.map(roleDto, Role.class));
  }

  @Override
  public Role getById(Integer id) {
    log.info("===== get role by id =====");
    return roleRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Role", Long.valueOf(id)));
  }

  @Override
  public List<Role> getAll() {
    log.info("===== getAll Role =====");
    return roleRepository.findAll();
  }

  @Override
  public List<Role> filterRole(RoleEnum search) {
    return roleRepository.findByNameLike(search);
  }

  @Override
  public List<Role> saveAsList(List<RoleDto> roleDtos) {
    log.info("===== Save Role List =====");
    List<Role> list = roleDtos.stream().map(roleDto -> mapper.map(roleDto, Role.class)).toList();
    return roleRepository.saveAll(list);
  }

  @Override
  public void assignPermission(String name, Set<PermissionEnum> permissions) {
    Role role = this.getByName(name);
    if (role == null) {
      log.error("===== role name is not found =====");
      throw new ResourceNotFoundException("Role", Long.valueOf(name));
    }
    List<Permission> permissions1 = permissionRepository.findAllByNameIn(new ArrayList<>(permissions));
    role.setPermissions(new HashSet<>(permissions1));
    roleRepository.save(role);
    log.info("===== assign permission successfully=====");
  }

  @Override
  public Role getByName(String name) {
    log.info("===== Find role by name =====");
    return roleRepository.findByName(RoleEnum.valueOf(name));
  }

  @Override
  public List<Permission> getPermissionFromRole(Long roleId) {
    log.info("===== Get permissions from role =====");
    return getById(Math.toIntExact(roleId)).getPermissions().stream().toList();
  }
}

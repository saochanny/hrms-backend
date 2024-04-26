package com.chanty.hrms.service.impl;

import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.exception.ResourceNotFoundException;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.repository.setup.RoleRepository;
import com.chanty.hrms.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
  private final ModelMapper mapper;
  private final RoleRepository roleRepository;

  @Override
  public Role createRole(RoleDto roleDto) {
    return roleRepository.save(mapper.map(roleDto, Role.class));
  }

  @Override
  public Role getById(Integer id) {
    return roleRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Role", Long.valueOf(id)));
  }

  @Override
  public List<Role> getAll() {
    return roleRepository.findAll();
  }

  @Override
  public List<Role> saveAsList(List<RoleDto> roleDtos) {
    List<Role> list = roleDtos.stream().map(roleDto -> mapper.map(roleDto, Role.class)).toList();
    return roleRepository.saveAll(list);
  }
}

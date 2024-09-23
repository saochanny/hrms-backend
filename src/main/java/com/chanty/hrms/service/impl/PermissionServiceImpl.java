package com.chanty.hrms.service.impl;

import com.chanty.hrms.model.setup.Permission;
import com.chanty.hrms.repository.setup.PermissionRepository;
import com.chanty.hrms.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
}

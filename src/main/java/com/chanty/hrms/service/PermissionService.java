package com.chanty.hrms.service;

import com.chanty.hrms.model.setup.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();
}

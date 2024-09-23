package com.chanty.hrms.controller;

import com.chanty.hrms.model.setup.Permission;
import com.chanty.hrms.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping
    public List<Permission> getPermissions() {
        return permissionService.findAll();
    }
}

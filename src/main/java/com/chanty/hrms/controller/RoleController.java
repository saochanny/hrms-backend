package com.chanty.hrms.controller;

import com.chanty.hrms.common.constant.PermissionEnum;
import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.model.setup.Permission;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.common.constant.RoleEnum;
import com.chanty.hrms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok(roleService.createRole(roleDto));
    }
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAll());
    }
    @GetMapping("/filter")
    public ResponseEntity<List<Role>> findRoleByContent(@RequestParam RoleEnum search){
        return ResponseEntity.ok(roleService.filterRole(search));
    }

    @PutMapping("/{roleName}")
    public void assignPermissionToRole(@PathVariable String roleName, @RequestBody Set<PermissionEnum> permissions){
        roleService.assignPermission(roleName, permissions);
    }
    @GetMapping("/{id}/permission")
    public List<Permission> getPermissionFromRole(@PathVariable(name = "id") Long roleId){
        return roleService.getPermissionFromRole(roleId);
    }
}

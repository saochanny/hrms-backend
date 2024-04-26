package com.chanty.hrms.controller;

import com.chanty.hrms.dto.role.RoleDto;
import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.RoleEnum;
import com.chanty.hrms.service.RoleService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

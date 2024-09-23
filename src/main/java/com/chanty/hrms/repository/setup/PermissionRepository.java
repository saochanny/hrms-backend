package com.chanty.hrms.repository.setup;

import com.chanty.hrms.common.constant.PermissionEnum;
import com.chanty.hrms.model.setup.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByNameIn(List<PermissionEnum> names);
}

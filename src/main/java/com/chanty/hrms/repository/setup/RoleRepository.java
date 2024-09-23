package com.chanty.hrms.repository.setup;

import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.common.constant.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role , Integer> {
    Role findByName(RoleEnum roleEnum);
    Set<Role> findByIdIsIn(Set<Integer> ids);
    List<Role> findByNameLike(RoleEnum search);
}

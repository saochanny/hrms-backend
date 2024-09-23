package com.chanty.hrms.repository.setup;

import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUsername(@Email String email, String username);
    Optional<User> findByUsername(String username);
    Boolean existsUserByEmail(String email);
    Boolean existsUserByUsername(String username);
    List<User> findAllByRolesIs(Set<Role> roles);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
}

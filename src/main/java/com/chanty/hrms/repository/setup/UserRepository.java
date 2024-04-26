package com.chanty.hrms.repository.setup;

import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailOrUsername(@Email String email, String username);
    Optional<User> findByUsername(String username);
    Boolean existsUserByEmail(String email);
    Boolean existsUserByUsername(String username);
    List<User> findAllByRolesIs(Set<Role> roles);
}

package com.chanty.hrms.config;

import static com.chanty.hrms.model.setup.RoleEnum.SUPER_ADMIN;

import com.chanty.hrms.model.setup.Role;
import com.chanty.hrms.model.setup.RoleEnum;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.RoleRepository;
import com.chanty.hrms.repository.setup.UserRepository;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class StartupConfig implements CommandLineRunner {
  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final ModelMapper mapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void run(String... args) throws Exception {
    List<Role> roles = roleRepository.findAll();
    List<User> users = userRepository.findAll();
    if (roles.isEmpty() && users.isEmpty()) {
      defineRoles();
      defineSuperAdmin();
    }
  }

  private void defineRoles() {
    Set<RoleEnum> enumSet = EnumSet.allOf(RoleEnum.class);
    Set<Role> roles =
        enumSet.stream()
            .map(
                roleEnum -> {
                  Role role = new Role();
                  String des = "This role use for " + roleEnum.getName().toLowerCase();
                  role.setName(roleEnum);
                  role.setDescription(des);
                  return role;
                })
            .collect(Collectors.toSet());
    List<Role> saveAll = roleRepository.saveAll(roles);
    log.info("=== Role have been defined : {}", saveAll);
  }

  private void defineSuperAdmin() {
    Role role = roleRepository.findByName(SUPER_ADMIN);
    User user = new User();
    user.setEmail("admin@gmail.com");
    user.setLastName("admin");
    user.setFirstName("admin");
    user.setUsername("admin");
    user.setIsEnable(true);
    user.setPassword(passwordEncoder.encode("admin"));
    user.setRoles(Set.of(role));
    User save = userRepository.save(user);
    log.info("=== Admin user have been create : {}", save);
  }
}

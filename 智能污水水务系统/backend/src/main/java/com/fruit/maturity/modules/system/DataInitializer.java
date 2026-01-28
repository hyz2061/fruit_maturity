package com.fruit.maturity.modules.system;

import com.fruit.maturity.modules.system.entity.SysRole;
import com.fruit.maturity.modules.system.entity.SysUser;
import com.fruit.maturity.modules.system.entity.SysUserRole;
import com.fruit.maturity.modules.system.repo.SysRoleRepository;
import com.fruit.maturity.modules.system.repo.SysUserRepository;
import com.fruit.maturity.modules.system.repo.SysUserRoleRepository;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
  private final SysUserRepository userRepository;
  private final SysRoleRepository roleRepository;
  private final SysUserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  public DataInitializer(SysUserRepository userRepository,
      SysRoleRepository roleRepository,
      SysUserRoleRepository userRoleRepository,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userRoleRepository = userRoleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) {
    SysRole adminRole = roleRepository.findByCode("ROLE_ADMIN")
      .orElseGet(() -> {
        SysRole role = new SysRole();
        role.setCode("ROLE_ADMIN");
        role.setName("管理员");
        return roleRepository.save(role);
      });

    SysRole userRole = roleRepository.findByCode("ROLE_USER")
      .orElseGet(() -> {
        SysRole role = new SysRole();
        role.setCode("ROLE_USER");
        role.setName("使用者");
        return roleRepository.save(role);
      });

    Optional<SysUser> adminOpt = userRepository.findByUsername("admin");
    if (adminOpt.isEmpty()) {
      SysUser admin = new SysUser();
      admin.setUsername("admin");
      admin.setPassword(passwordEncoder.encode("Admin123!"));
      admin.setNickname("Admin");
      admin.setEnabled(true);
      admin = userRepository.save(admin);

      SysUserRole link = new SysUserRole();
      link.setUserId(admin.getId());
      link.setRoleId(adminRole.getId());
      userRoleRepository.save(link);
    }

    Optional<SysUser> userOpt = userRepository.findByUsername("user");
    if (userOpt.isEmpty()) {
      SysUser user = new SysUser();
      user.setUsername("user");
      user.setPassword(passwordEncoder.encode("User123!"));
      user.setNickname("User");
      user.setEnabled(true);
      user = userRepository.save(user);

      SysUserRole link = new SysUserRole();
      link.setUserId(user.getId());
      link.setRoleId(userRole.getId());
      userRoleRepository.save(link);
    }
  }
}

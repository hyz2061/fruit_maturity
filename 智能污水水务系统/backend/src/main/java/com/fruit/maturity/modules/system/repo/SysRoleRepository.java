package com.fruit.maturity.modules.system.repo;

import com.fruit.maturity.modules.system.entity.SysRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
  Optional<SysRole> findByCode(String code);
}

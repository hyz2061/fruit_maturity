package com.fruit.maturity.modules.system.repo;

import com.fruit.maturity.modules.system.entity.SysUserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRoleRepository extends JpaRepository<SysUserRole, Long> {
  List<SysUserRole> findByUserId(Long userId);
}

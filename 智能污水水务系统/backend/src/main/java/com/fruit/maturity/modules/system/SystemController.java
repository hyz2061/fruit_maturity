package com.fruit.maturity.modules.system;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.system.repo.AuditLogRepository;
import com.fruit.maturity.modules.system.repo.DictItemRepository;
import com.fruit.maturity.modules.system.repo.SysRoleRepository;
import com.fruit.maturity.modules.system.repo.SysUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class SystemController {
  private final SysUserRepository userRepository;
  private final SysRoleRepository roleRepository;
  private final DictItemRepository dictRepository;
  private final AuditLogRepository auditRepository;

  public SystemController(SysUserRepository userRepository,
      SysRoleRepository roleRepository,
      DictItemRepository dictRepository,
      AuditLogRepository auditRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.dictRepository = dictRepository;
    this.auditRepository = auditRepository;
  }

  @GetMapping("/users")
  public ApiResult<?> users() {
    return ApiResult.ok(userRepository.findAll());
  }

  @GetMapping("/roles")
  public ApiResult<?> roles() {
    return ApiResult.ok(roleRepository.findAll());
  }

  @GetMapping("/dicts")
  public ApiResult<?> dicts() {
    return ApiResult.ok(dictRepository.findAll());
  }

  @GetMapping("/audits")
  public ApiResult<?> audits() {
    return ApiResult.ok(auditRepository.findAll());
  }
}

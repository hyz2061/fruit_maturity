package com.fruit.maturity.modules.auth;

import com.fruit.maturity.modules.auth.dto.AuthMeResp;
import com.fruit.maturity.modules.auth.dto.LoginReq;
import com.fruit.maturity.modules.auth.dto.LoginResp;
import com.fruit.maturity.modules.auth.dto.RegisterReq;
import com.fruit.maturity.modules.system.entity.SysRole;
import com.fruit.maturity.modules.system.entity.SysUser;
import com.fruit.maturity.modules.system.entity.SysUserRole;
import com.fruit.maturity.modules.system.repo.SysRoleRepository;
import com.fruit.maturity.modules.system.repo.SysUserRepository;
import com.fruit.maturity.modules.system.repo.SysUserRoleRepository;
import com.fruit.maturity.security.JwtUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
  private final SysUserRepository userRepository;
  private final SysUserRoleRepository userRoleRepository;
  private final SysRoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(SysUserRepository userRepository,
      SysUserRoleRepository userRoleRepository,
      SysRoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public LoginResp login(LoginReq req) {
    SysUser user = userRepository.findByUsername(req.getUsername())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials"));

    if (!Boolean.TRUE.equals(user.getEnabled())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user disabled");
    }

    if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getUsername());
    return new LoginResp(token, user.getUsername());
  }

  public void register(RegisterReq req) {
    if (userRepository.existsByUsername(req.getUsername())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username already exists");
    }

    SysUser user = new SysUser();
    user.setUsername(req.getUsername());
    user.setPassword(passwordEncoder.encode(req.getPassword()));
    user.setEnabled(true);
    user = userRepository.save(user);

    String reqRoleCode = req.getRoleCode();
    final String roleCode = (reqRoleCode == null || reqRoleCode.isBlank()) ? "ROLE_USER" : reqRoleCode;

    SysRole role = roleRepository.findByCode(roleCode)
      .orElseGet(() -> {
        SysRole newRole = new SysRole();
        newRole.setCode(roleCode);
        newRole.setName(roleCode.equals("ROLE_ADMIN") ? "管理员" : "使用者");
        return roleRepository.save(newRole);
      });

    SysUserRole link = new SysUserRole();
    link.setUserId(user.getId());
    link.setRoleId(role.getId());
    userRoleRepository.save(link);
  }

  public AuthMeResp me(String username) {
    SysUser user = userRepository.findByUsername(username)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));

    List<Long> roleIds = userRoleRepository.findByUserId(user.getId()).stream()
      .map(SysUserRole::getRoleId)
      .collect(Collectors.toList());

    List<String> roles = roleRepository.findAllById(roleIds).stream()
      .map(SysRole::getCode)
      .collect(Collectors.toList());

    return new AuthMeResp(user.getUsername(), roles);
  }
}

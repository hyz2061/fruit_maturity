package com.fruit.maturity.security;

import com.fruit.maturity.modules.system.entity.SysRole;
import com.fruit.maturity.modules.system.entity.SysUser;
import com.fruit.maturity.modules.system.entity.SysUserRole;
import com.fruit.maturity.modules.system.repo.SysRoleRepository;
import com.fruit.maturity.modules.system.repo.SysUserRepository;
import com.fruit.maturity.modules.system.repo.SysUserRoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final SysUserRepository userRepository;
  private final SysUserRoleRepository userRoleRepository;
  private final SysRoleRepository roleRepository;

  public CustomUserDetailsService(SysUserRepository userRepository,
      SysUserRoleRepository userRoleRepository,
      SysRoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.userRoleRepository = userRoleRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SysUser user = userRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException("user not found"));

    List<SysUserRole> userRoles = userRoleRepository.findByUserId(user.getId());
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (SysUserRole ur : userRoles) {
      Optional<SysRole> roleOpt = roleRepository.findById(ur.getRoleId());
      roleOpt.ifPresent(role -> authorities.add(new SimpleGrantedAuthority(role.getCode())));
    }

    return new SecurityUser(user, authorities);
  }
}

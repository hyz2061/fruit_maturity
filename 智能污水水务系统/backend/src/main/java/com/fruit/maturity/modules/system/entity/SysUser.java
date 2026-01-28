package com.fruit.maturity.modules.system.entity;

import com.fruit.maturity.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {
  @Column(nullable = false, unique = true, length = 64)
  private String username;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(length = 64)
  private String nickname;

  @Column(nullable = false)
  private Boolean enabled = true;
}

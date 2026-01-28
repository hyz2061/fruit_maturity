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
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {
  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long roleId;
}

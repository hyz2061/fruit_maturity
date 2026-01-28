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
@Table(name = "audit_log")
public class AuditLog extends BaseEntity {
  @Column(nullable = false, length = 64)
  private String actor;

  @Column(nullable = false, length = 128)
  private String action;

  @Column(length = 64)
  private String ip;
}

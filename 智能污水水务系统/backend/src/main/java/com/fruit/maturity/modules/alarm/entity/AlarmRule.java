package com.fruit.maturity.modules.alarm.entity;

import com.fruit.maturity.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "alarm_rule")
public class AlarmRule extends BaseEntity {
  @Column(nullable = false, length = 64)
  private String name;

  @Column(nullable = false, length = 512)
  private String expression;

  private Boolean enabled = true;
}

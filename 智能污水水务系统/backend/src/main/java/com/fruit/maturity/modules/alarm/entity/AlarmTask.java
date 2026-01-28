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
@Table(name = "alarm_task")
public class AlarmTask extends BaseEntity {
  @Column(nullable = false)
  private Long eventId;

  @Column(length = 32)
  private String status;

  @Column(length = 64)
  private String assignee;
}

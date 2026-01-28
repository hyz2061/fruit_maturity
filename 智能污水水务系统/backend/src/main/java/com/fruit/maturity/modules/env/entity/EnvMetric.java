package com.fruit.maturity.modules.env.entity;

import com.fruit.maturity.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "env_metric")
public class EnvMetric extends BaseEntity {
  @Column(nullable = false)
  private Long greenhouseId;

  @Column(nullable = false, length = 64)
  private String metric;

  @Column(nullable = false)
  private Double value;

  @Column(nullable = false)
  private LocalDateTime ts;
}

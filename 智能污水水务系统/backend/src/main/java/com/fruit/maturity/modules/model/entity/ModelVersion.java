package com.fruit.maturity.modules.model.entity;

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
@Table(name = "model_version")
public class ModelVersion extends BaseEntity {
  @Column(nullable = false, length = 64)
  private String name;

  private Double map;

  private Double recall;

  private Boolean active = false;

  private LocalDateTime deployedAt;
}

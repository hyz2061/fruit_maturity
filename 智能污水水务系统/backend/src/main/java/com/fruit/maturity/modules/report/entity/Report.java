package com.fruit.maturity.modules.report.entity;

import com.fruit.maturity.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "report")
public class Report extends BaseEntity {
  @Column(nullable = false, length = 32)
  private String type;

  @Column(nullable = false, length = 64)
  private String period;

  @Column(length = 255)
  private String url;
}

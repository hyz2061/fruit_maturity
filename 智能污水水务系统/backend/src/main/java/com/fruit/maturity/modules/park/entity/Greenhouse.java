package com.fruit.maturity.modules.park.entity;

import com.fruit.maturity.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "greenhouse")
public class Greenhouse extends BaseEntity {
  @Column(nullable = false)
  private Long parkId;

  @Column(nullable = false, length = 64)
  private String code;

  @Column(nullable = false, length = 64)
  private String name;
}

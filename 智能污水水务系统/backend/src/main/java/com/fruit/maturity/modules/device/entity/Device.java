package com.fruit.maturity.modules.device.entity;

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
@Table(name = "device")
public class Device extends BaseEntity {
  @Column(nullable = false, length = 32)
  private String type; // sensor/gateway/camera

  @Column(length = 64)
  private String name;

  @Column(length = 64)
  private String code;

  private Long greenhouseId;

  @Column(length = 255)
  private String streamUrl;

  @Column(length = 32)
  private String status;

  private LocalDateTime lastSeen;
}

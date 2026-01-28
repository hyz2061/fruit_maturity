package com.fruit.maturity.modules.event.entity;

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
@Table(name = "event_record")
public class Event extends BaseEntity {
  @Column(nullable = false)
  private Long greenhouseId;

  @Column(nullable = false, length = 64)
  private String type;

  @Column(length = 32)
  private String severity;

  @Column(length = 32)
  private String status;

  @Column(length = 255)
  private String imageUrl;

  @Column(length = 255)
  private String thumbUrl;

  @Column(length = 255)
  private String annotatedUrl;

  @Column(length = 64)
  private String modelVersion;

  private Double confidence;

  private Double ripeRatio;

  private LocalDateTime captureTime;

  @Column(length = 32)
  private String reviewLabel;

  @Column(length = 255)
  private String reviewNote;

  private LocalDateTime reviewedAt;

  @Column(length = 64)
  private String reviewedBy;
}

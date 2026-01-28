package com.fruit.maturity.modules.event.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EventCreateReq {
  @NotNull
  private Long greenhouseId;

  @NotBlank
  private String type;

  private String severity;
  private String status;
  private String imageUrl;
  private String thumbUrl;
  private String annotatedUrl;
  private String modelVersion;
  private Double confidence;
  private Double ripeRatio;
  private LocalDateTime captureTime;
}

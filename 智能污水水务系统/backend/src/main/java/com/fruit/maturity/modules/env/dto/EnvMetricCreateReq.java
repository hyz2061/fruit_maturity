package com.fruit.maturity.modules.env.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EnvMetricCreateReq {
  @NotNull
  private Long greenhouseId;

  @NotBlank
  private String metric;

  @NotNull
  private Double value;

  private LocalDateTime ts;
}

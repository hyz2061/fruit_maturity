package com.fruit.maturity.modules.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventStatusReq {
  @NotBlank
  private String status;
}

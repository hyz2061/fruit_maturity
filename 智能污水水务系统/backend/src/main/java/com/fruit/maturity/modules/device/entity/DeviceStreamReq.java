package com.fruit.maturity.modules.device.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeviceStreamReq {
  @NotBlank
  private String streamUrl;
}

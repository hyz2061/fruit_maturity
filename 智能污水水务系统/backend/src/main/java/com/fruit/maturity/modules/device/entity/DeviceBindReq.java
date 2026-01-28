package com.fruit.maturity.modules.device.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceBindReq {
  @NotNull
  private Long greenhouseId;
}

package com.fruit.maturity.modules.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelDeployReq {
  @NotNull
  private Long id;
}

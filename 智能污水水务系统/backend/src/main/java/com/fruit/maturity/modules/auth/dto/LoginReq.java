package com.fruit.maturity.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginReq {
  @NotBlank
  private String username;

  @NotBlank
  private String password;
}

package com.fruit.maturity.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReq {
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  // ROLE_USER or ROLE_ADMIN (demo)
  private String roleCode;
}

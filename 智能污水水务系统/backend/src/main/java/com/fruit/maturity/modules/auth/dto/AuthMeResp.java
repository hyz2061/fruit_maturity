package com.fruit.maturity.modules.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthMeResp {
  private String username;
  private List<String> roles;
}

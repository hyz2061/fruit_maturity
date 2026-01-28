package com.fruit.maturity.modules.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiChatReq {
  @NotBlank
  private String message;
  private String sessionId;
}

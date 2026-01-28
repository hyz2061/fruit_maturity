package com.fruit.maturity.modules.ai.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiChatResp {
  private String answer;
  private List<String> suggestions;
  private LocalDateTime ts;
}

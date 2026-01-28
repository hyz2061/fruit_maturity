package com.fruit.maturity.modules.ai;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.ai.dto.AiChatReq;
import com.fruit.maturity.modules.ai.dto.AiChatResp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {
  private final AiService aiService;

  public AiController(AiService aiService) {
    this.aiService = aiService;
  }

  @PostMapping("/chat")
  public ApiResult<AiChatResp> chat(@Valid @RequestBody AiChatReq req) {
    return ApiResult.ok(aiService.chat(req));
  }
}

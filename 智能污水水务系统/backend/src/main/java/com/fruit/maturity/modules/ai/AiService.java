package com.fruit.maturity.modules.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fruit.maturity.modules.ai.dto.AiChatReq;
import com.fruit.maturity.modules.ai.dto.AiChatResp;
import com.fruit.maturity.modules.env.entity.EnvMetric;
import com.fruit.maturity.modules.env.repo.EnvMetricRepository;
import com.fruit.maturity.modules.event.repo.EventRepository;
import com.fruit.maturity.modules.model.entity.ModelVersion;
import com.fruit.maturity.modules.model.repo.ModelVersionRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AiService {
  private final EventRepository eventRepository;
  private final EnvMetricRepository envRepository;
  private final ModelVersionRepository modelRepository;
  private final ObjectMapper objectMapper;

  private final WebClient webClient;

  @Value("${ai.provider:mock}")
  private String provider;

  @Value("${ai.deepseek.api-key:}")
  private String apiKey;

  public AiService(EventRepository eventRepository,
      EnvMetricRepository envRepository,
      ModelVersionRepository modelRepository,
      ObjectMapper objectMapper,
      WebClient.Builder builder,
      @Value("${ai.deepseek.base-url:https://api.deepseek.com}") String baseUrl) {
    this.eventRepository = eventRepository;
    this.envRepository = envRepository;
    this.modelRepository = modelRepository;
    this.objectMapper = objectMapper;
    this.webClient = builder.baseUrl(baseUrl).build();
  }

  public AiChatResp chat(AiChatReq req) {
    if ("deepseek".equalsIgnoreCase(provider) && apiKey != null && !apiKey.isBlank()) {
      try {
        return deepseek(req.getMessage());
      } catch (Exception ex) {
        return mock(req.getMessage());
      }
    }
    return mock(req.getMessage());
  }

  private AiChatResp deepseek(String message) throws Exception {
    Map<String, Object> payload = new HashMap<>();
    payload.put("model", "deepseek-chat");
    payload.put("messages", List.of(
      Map.of("role", "system", "content", "你是农业果实成熟度监测平台的分析助手。"),
      Map.of("role", "user", "content", message)
    ));

    String response = webClient.post()
      .uri("/v1/chat/completions")
      .header("Authorization", "Bearer " + apiKey)
      .bodyValue(payload)
      .retrieve()
      .bodyToMono(String.class)
      .block();

    if (response == null || response.isBlank()) {
      return mock(message);
    }

    JsonNode root = objectMapper.readTree(response);
    JsonNode choices = root.path("choices");
    String content = choices.isArray() && choices.size() > 0
      ? choices.get(0).path("message").path("content").asText()
      : "已收到问题，正在分析";

    return new AiChatResp(content, List.of("查看实时监控", "打开事件中心", "导出周报"), LocalDateTime.now());
  }

  private AiChatResp mock(String message) {
    String msg = message == null ? "" : message.toLowerCase(Locale.ROOT);

    long totalEvents = eventRepository.count();
    long pending = eventRepository.countByStatus("待确认");
    long processing = eventRepository.countByStatus("处理中");

    Optional<ModelVersion> activeModel = modelRepository.findFirstByActiveTrue();
    String modelName = activeModel.map(ModelVersion::getName).orElse("--");

    Optional<EnvMetric> tempOpt = envRepository.findTopByMetricOrderByTsDesc("temperature");
    String temp = tempOpt.map(m -> String.format("%.1f℃", m.getValue())).orElse("--");

    StringBuilder answer = new StringBuilder();
    answer.append("已汇总当前态势：事件总数 ").append(totalEvents)
      .append("，待确认 ").append(pending)
      .append("，处理中 ").append(processing)
      .append("。当前在线模型：").append(modelName)
      .append("，最新温度：").append(temp).append("。\n");

    if (msg.contains("成熟") || msg.contains("mature")) {
      answer.append("建议优先关注成熟度偏低事件，并结合温湿度与光照曲线判断是否存在持续偏离。\n");
    }
    if (msg.contains("告警")) {
      answer.append("可在【告警与任务】查看近期规则触发与处理效率，必要时调整阈值。\n");
    }
    if (msg.contains("模型")) {
      answer.append("如需提升识别稳定性，可在【模型管理】进行灰度发布与回滚验证。\n");
    }

    return new AiChatResp(answer.toString().trim(), List.of("查看实时监控", "打开事件中心", "导出周报"), LocalDateTime.now());
  }
}

package com.fruit.maturity.modules.env;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.env.entity.EnvMetric;
import com.fruit.maturity.modules.env.repo.EnvMetricRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvController {
  private final EnvMetricRepository envMetricRepository;

  public EnvController(EnvMetricRepository envMetricRepository) {
    this.envMetricRepository = envMetricRepository;
  }

  @GetMapping("/env/metrics")
  public ApiResult<List<EnvMetric>> metrics(
      @RequestParam(required = false) Long greenhouseId,
      @RequestParam(required = false) String metric,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

    if (greenhouseId != null && metric != null && start != null && end != null) {
      return ApiResult.ok(envMetricRepository.findByGreenhouseIdAndMetricAndTsBetween(
        greenhouseId, metric, start, end));
    }
    if (greenhouseId != null && metric != null) {
      return ApiResult.ok(envMetricRepository.findByGreenhouseIdAndMetric(greenhouseId, metric));
    }
    if (greenhouseId != null) {
      return ApiResult.ok(envMetricRepository.findByGreenhouseId(greenhouseId));
    }
    if (metric != null && !metric.isBlank()) {
      return ApiResult.ok(envMetricRepository.findByMetric(metric));
    }
    return ApiResult.ok(envMetricRepository.findAll());
  }
}

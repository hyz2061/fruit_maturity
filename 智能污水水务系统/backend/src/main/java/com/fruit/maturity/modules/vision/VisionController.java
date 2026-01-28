package com.fruit.maturity.modules.vision;

import com.fruit.maturity.common.ApiResult;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisionController {
  @GetMapping("/vision/stat")
  public ApiResult<Map<String, Object>> stat() {
    Map<String, Object> data = new HashMap<>();
    data.put("classDistribution", Collections.emptyList());
    data.put("severityTrend", Collections.emptyList());
    data.put("heatmap", Collections.emptyList());
    return ApiResult.ok(data);
  }

  @GetMapping("/vision/dataset")
  public ApiResult<?> dataset() {
    return ApiResult.ok(Collections.emptyList());
  }
}

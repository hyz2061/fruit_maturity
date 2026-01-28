package com.fruit.maturity.modules.model;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.model.dto.ModelDeployReq;
import com.fruit.maturity.modules.model.entity.ModelVersion;
import com.fruit.maturity.modules.model.repo.ModelVersionRepository;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ModelController {
  private final ModelVersionRepository modelRepository;

  public ModelController(ModelVersionRepository modelRepository) {
    this.modelRepository = modelRepository;
  }

  @GetMapping("/models")
  public ApiResult<List<ModelVersion>> list() {
    return ApiResult.ok(modelRepository.findAll());
  }

  @PostMapping("/models/deploy")
  public ApiResult<ModelVersion> deploy(@Valid @RequestBody ModelDeployReq req) {
    ModelVersion target = modelRepository.findById(req.getId())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "model not found"));

    List<ModelVersion> all = modelRepository.findAll();
    for (ModelVersion mv : all) {
      mv.setActive(false);
    }
    modelRepository.saveAll(all);

    target.setActive(true);
    target.setDeployedAt(LocalDateTime.now());
    return ApiResult.ok(modelRepository.save(target));
  }
}

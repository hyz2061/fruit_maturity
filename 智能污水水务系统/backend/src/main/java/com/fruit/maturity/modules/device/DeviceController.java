package com.fruit.maturity.modules.device;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.device.entity.Device;
import com.fruit.maturity.modules.device.entity.DeviceBindReq;
import com.fruit.maturity.modules.device.entity.DeviceStreamReq;
import com.fruit.maturity.modules.device.repo.DeviceRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DeviceController {
  private final DeviceRepository deviceRepository;

  public DeviceController(DeviceRepository deviceRepository) {
    this.deviceRepository = deviceRepository;
  }

  @GetMapping("/devices")
  public ApiResult<List<Device>> list(@RequestParam(required = false) String type) {
    if (type != null && !type.isBlank()) {
      return ApiResult.ok(deviceRepository.findByType(type));
    }
    return ApiResult.ok(deviceRepository.findAll());
  }

  @PatchMapping("/devices/{id}/bind")
  public ApiResult<Device> bind(@PathVariable Long id, @Valid @RequestBody DeviceBindReq req) {
    Device device = deviceRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "device not found"));
    device.setGreenhouseId(req.getGreenhouseId());
    return ApiResult.ok(deviceRepository.save(device));
  }

  @PatchMapping("/devices/{id}/stream")
  public ApiResult<Device> updateStream(@PathVariable Long id, @Valid @RequestBody DeviceStreamReq req) {
    Device device = deviceRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "device not found"));
    device.setStreamUrl(req.getStreamUrl());
    return ApiResult.ok(deviceRepository.save(device));
  }
}

package com.fruit.maturity.modules.park;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.park.repo.GreenhouseRepository;
import com.fruit.maturity.modules.park.repo.ParkRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ParkController {
  private final ParkRepository parkRepository;
  private final GreenhouseRepository greenhouseRepository;

  public ParkController(ParkRepository parkRepository, GreenhouseRepository greenhouseRepository) {
    this.parkRepository = parkRepository;
    this.greenhouseRepository = greenhouseRepository;
  }

  @GetMapping("parks")
  public ApiResult<?> parks() {
    return ApiResult.ok(parkRepository.findAll());
  }

  @GetMapping("greenhouses")
  public ApiResult<?> greenhouses(@RequestParam(required = false) Long parkId) {
    if (parkId == null) {
      return ApiResult.ok(greenhouseRepository.findAll());
    }
    return ApiResult.ok(greenhouseRepository.findByParkId(parkId));
  }
}

package com.fruit.maturity.modules.device.repo;

import com.fruit.maturity.modules.device.entity.Device;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
  List<Device> findByType(String type);
}

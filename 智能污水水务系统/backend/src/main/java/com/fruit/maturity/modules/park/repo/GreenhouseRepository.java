package com.fruit.maturity.modules.park.repo;

import com.fruit.maturity.modules.park.entity.Greenhouse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenhouseRepository extends JpaRepository<Greenhouse, Long> {
  List<Greenhouse> findByParkId(Long parkId);
}

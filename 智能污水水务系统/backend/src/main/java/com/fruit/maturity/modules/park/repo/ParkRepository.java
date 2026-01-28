package com.fruit.maturity.modules.park.repo;

import com.fruit.maturity.modules.park.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkRepository extends JpaRepository<Park, Long> {
}

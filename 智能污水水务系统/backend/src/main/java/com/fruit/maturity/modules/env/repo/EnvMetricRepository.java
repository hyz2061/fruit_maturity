package com.fruit.maturity.modules.env.repo;

import com.fruit.maturity.modules.env.entity.EnvMetric;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnvMetricRepository extends JpaRepository<EnvMetric, Long> {
  List<EnvMetric> findByGreenhouseId(Long greenhouseId);
  List<EnvMetric> findByMetric(String metric);
  List<EnvMetric> findByGreenhouseIdAndMetric(Long greenhouseId, String metric);
  List<EnvMetric> findByGreenhouseIdAndMetricAndTsBetween(
    Long greenhouseId, String metric, LocalDateTime start, LocalDateTime end);

  /** Returns the latest EnvMetric for a specific greenhouse and metric. */
  Optional<EnvMetric> findTopByGreenhouseIdAndMetricOrderByTsDesc(Long greenhouseId, String metric);

  /** Returns the latest EnvMetric for a given metric type. */
  Optional<EnvMetric> findTopByMetricOrderByTsDesc(String metric);
}

package com.fruit.maturity.modules.model.repo;

import com.fruit.maturity.modules.model.entity.ModelVersion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelVersionRepository extends JpaRepository<ModelVersion, Long> {
  Optional<ModelVersion> findFirstByActiveTrue();
}

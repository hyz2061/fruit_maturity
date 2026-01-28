package com.fruit.maturity.modules.system.repo;

import com.fruit.maturity.modules.system.entity.DictItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DictItemRepository extends JpaRepository<DictItem, Long> {
}

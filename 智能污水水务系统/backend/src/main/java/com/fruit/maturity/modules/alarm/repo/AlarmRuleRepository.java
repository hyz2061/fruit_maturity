package com.fruit.maturity.modules.alarm.repo;

import com.fruit.maturity.modules.alarm.entity.AlarmRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Long> {
}

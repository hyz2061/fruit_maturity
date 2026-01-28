package com.fruit.maturity.modules.alarm.repo;

import com.fruit.maturity.modules.alarm.entity.AlarmTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmTaskRepository extends JpaRepository<AlarmTask, Long> {
}

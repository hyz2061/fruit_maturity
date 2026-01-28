package com.fruit.maturity.modules.alarm;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.modules.alarm.repo.AlarmRuleRepository;
import com.fruit.maturity.modules.alarm.repo.AlarmTaskRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlarmController {
  private final AlarmRuleRepository ruleRepository;
  private final AlarmTaskRepository taskRepository;

  public AlarmController(AlarmRuleRepository ruleRepository, AlarmTaskRepository taskRepository) {
    this.ruleRepository = ruleRepository;
    this.taskRepository = taskRepository;
  }

  @GetMapping("/alarms/rules")
  public ApiResult<?> rules() {
    return ApiResult.ok(ruleRepository.findAll());
  }

  @GetMapping("/alarms/tasks")
  public ApiResult<?> tasks() {
    return ApiResult.ok(taskRepository.findAll());
  }
}

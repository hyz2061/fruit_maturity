USE fruit_maturity;

INSERT IGNORE INTO park (id, name, code, created_at, updated_at) VALUES
  (1, '园区A', 'PARK-A', NOW(), NOW()),
  (2, '园区B', 'PARK-B', NOW(), NOW());

INSERT IGNORE INTO greenhouse (id, park_id, code, name, created_at, updated_at) VALUES
  (1, 1, 'GH-001', '一号棚', NOW(), NOW()),
  (2, 1, 'GH-002', '二号棚', NOW(), NOW()),
  (3, 2, 'GH-101', '一号棚', NOW(), NOW());

INSERT IGNORE INTO device (id, type, name, code, greenhouse_id, stream_url, status, last_seen, created_at, updated_at) VALUES
  (1, 'camera', 'Camera-01', 'CAM-001', 1, '', 'online', NOW(), NOW(), NOW()),
  (2, 'sensor', 'EnvSensor-01', 'SEN-001', 1, '', 'online', NOW(), NOW(), NOW()),
  (3, 'gateway', 'Gateway-01', 'GW-001', 2, '', 'online', NOW(), NOW(), NOW());

INSERT IGNORE INTO model_version (id, name, map, recall, active, deployed_at, created_at, updated_at) VALUES
  (1, 'v1.2.0', 0.81, 0.76, 1, NOW(), NOW(), NOW()),
  (2, 'v1.1.0', 0.78, 0.73, 0, DATE_SUB(NOW(), INTERVAL 7 DAY), NOW(), NOW());

INSERT IGNORE INTO event_record (
  id, greenhouse_id, type, severity, status, image_url, thumb_url, annotated_url,
  model_version, confidence, ripe_ratio, capture_time, review_label, review_note,
  reviewed_at, reviewed_by, created_at, updated_at
) VALUES
  (1, 1, '成熟度偏低', '中', '待确认', '', '', '', 'v1.2.0', 0.91, 0.42, NOW(), NULL, NULL, NULL, NULL, NOW(), NOW()),
  (2, 2, '病害疑似', '高', '处理中', '', '', '', 'v1.2.0', 0.88, 0.28, NOW(), NULL, NULL, NULL, NULL, NOW(), NOW()),
  (3, 1, '成熟度达标', '低', '已完成', '', '', '', 'v1.1.0', 0.95, 0.78, NOW(), 'FALSE_POSITIVE', '样本纠错', NOW(), 'admin', NOW(), NOW());

INSERT IGNORE INTO env_metric (id, greenhouse_id, metric, value, ts, created_at, updated_at) VALUES
  (1, 1, 'temperature', 26.4, NOW(), NOW(), NOW()),
  (2, 1, 'humidity', 62.0, NOW(), NOW(), NOW()),
  (3, 1, 'co2', 980, NOW(), NOW(), NOW()),
  (4, 2, 'temperature', 27.1, NOW(), NOW(), NOW()),
  (5, 2, 'humidity', 58.5, NOW(), NOW(), NOW());

INSERT IGNORE INTO alarm_rule (id, name, expression, enabled, created_at, updated_at) VALUES
  (1, '成熟度偏低+高温', 'event.type=="成熟度偏低" && env.temperature>28', 1, NOW(), NOW());

INSERT IGNORE INTO alarm_task (id, event_id, status, assignee, created_at, updated_at) VALUES
  (1, 2, '处理中', '张三', NOW(), NOW());

INSERT IGNORE INTO report (id, type, period, url, created_at, updated_at) VALUES
  (1, '周报', '2026-W03', '', NOW(), NOW());

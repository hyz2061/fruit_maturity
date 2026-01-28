SET NAMES utf8mb4;

CREATE DATABASE IF NOT EXISTS fruit_maturity DEFAULT CHARACTER SET utf8mb4;
USE fruit_maturity;

CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(64) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(64),
  enabled TINYINT(1) NOT NULL DEFAULT 1,
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  code VARCHAR(64) NOT NULL UNIQUE,
  name VARCHAR(64) NOT NULL,
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_user_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  created_at DATETIME,
  updated_at DATETIME,
  UNIQUE KEY uk_user_role (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_dict_item (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  dict_type VARCHAR(64) NOT NULL,
  code VARCHAR(64) NOT NULL,
  name VARCHAR(128) NOT NULL,
  sort INT,
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS audit_log (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  actor VARCHAR(64) NOT NULL,
  action VARCHAR(128) NOT NULL,
  ip VARCHAR(64),
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS park (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  code VARCHAR(64),
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS greenhouse (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  park_id BIGINT NOT NULL,
  code VARCHAR(64) NOT NULL,
  name VARCHAR(64) NOT NULL,
  created_at DATETIME,
  updated_at DATETIME,
  KEY idx_greenhouse_park (park_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS device (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(32) NOT NULL,
  name VARCHAR(64),
  code VARCHAR(64),
  greenhouse_id BIGINT,
  stream_url VARCHAR(255),
  status VARCHAR(32),
  last_seen DATETIME,
  created_at DATETIME,
  updated_at DATETIME,
  KEY idx_device_greenhouse (greenhouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS env_metric (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  greenhouse_id BIGINT NOT NULL,
  metric VARCHAR(64) NOT NULL,
  value DOUBLE NOT NULL,
  ts DATETIME NOT NULL,
  created_at DATETIME,
  updated_at DATETIME,
  KEY idx_env_greenhouse (greenhouse_id),
  KEY idx_env_metric (metric),
  KEY idx_env_time (ts)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS event_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  greenhouse_id BIGINT NOT NULL,
  type VARCHAR(64) NOT NULL,
  severity VARCHAR(32),
  status VARCHAR(32),
  image_url VARCHAR(255),
  thumb_url VARCHAR(255),
  annotated_url VARCHAR(255),
  model_version VARCHAR(64),
  confidence DOUBLE,
  ripe_ratio DOUBLE,
  capture_time DATETIME,
  review_label VARCHAR(32),
  review_note VARCHAR(255),
  reviewed_at DATETIME,
  reviewed_by VARCHAR(64),
  created_at DATETIME,
  updated_at DATETIME,
  KEY idx_event_greenhouse (greenhouse_id),
  KEY idx_event_type (type),
  KEY idx_event_status (status),
  KEY idx_event_greenhouse_time (greenhouse_id, capture_time),
  KEY idx_event_ripe (ripe_ratio),
  KEY idx_event_created (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS alarm_rule (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  expression VARCHAR(512) NOT NULL,
  enabled TINYINT(1) DEFAULT 1,
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS alarm_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  event_id BIGINT NOT NULL,
  status VARCHAR(32),
  assignee VARCHAR(64),
  created_at DATETIME,
  updated_at DATETIME,
  KEY idx_alarm_event (event_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS model_version (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(64) NOT NULL,
  map DOUBLE,
  recall DOUBLE,
  active TINYINT(1) DEFAULT 0,
  deployed_at DATETIME,
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  type VARCHAR(32) NOT NULL,
  period VARCHAR(64) NOT NULL,
  url VARCHAR(255),
  created_at DATETIME,
  updated_at DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

package com.fruit.maturity.modules.system.repo;

import com.fruit.maturity.modules.system.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}

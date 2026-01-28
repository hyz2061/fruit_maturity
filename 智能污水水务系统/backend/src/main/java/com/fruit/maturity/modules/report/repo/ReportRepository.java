package com.fruit.maturity.modules.report.repo;

import com.fruit.maturity.modules.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}

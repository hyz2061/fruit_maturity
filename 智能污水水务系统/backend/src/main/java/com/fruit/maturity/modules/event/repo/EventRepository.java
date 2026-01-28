package com.fruit.maturity.modules.event.repo;

import com.fruit.maturity.modules.event.entity.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByGreenhouseId(Long greenhouseId);
  List<Event> findByType(String type);
  List<Event> findByStatus(String status);
  long countByStatus(String status);
  Optional<Event> findTopByOrderByCreatedAtDesc();
  Optional<Event> findTopByGreenhouseIdOrderByCreatedAtDesc(Long greenhouseId);
  List<Event> findByIdGreaterThanOrderByIdAsc(Long id, Pageable pageable);

  @Query("select e from Event e where "
    + "(:greenhouseId is null or e.greenhouseId = :greenhouseId) "
    + "and (:type is null or e.type like %:type%) "
    + "and (:status is null or e.status = :status) "
    + "and (:keyword is null or e.type like %:keyword%) "
    + "and (:fromTime is null or e.createdAt >= :fromTime) "
    + "and (:toTime is null or e.createdAt <= :toTime) "
    + "and (:minRipeRatio is null or e.ripeRatio >= :minRipeRatio)")
  Page<Event> findPageByFilter(@Param("greenhouseId") Long greenhouseId,
                               @Param("type") String type,
                               @Param("status") String status,
                               @Param("keyword") String keyword,
                               @Param("fromTime") LocalDateTime fromTime,
                               @Param("toTime") LocalDateTime toTime,
                               @Param("minRipeRatio") Double minRipeRatio,
                               Pageable pageable);
}

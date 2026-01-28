package com.fruit.maturity.modules.event;

import com.fruit.maturity.common.ApiResult;
import com.fruit.maturity.common.PageResult;
import com.fruit.maturity.modules.event.dto.EventCreateReq;
import com.fruit.maturity.modules.event.dto.EventReviewReq;
import com.fruit.maturity.modules.event.dto.EventStatusReq;
import com.fruit.maturity.modules.event.entity.Event;
import com.fruit.maturity.modules.event.repo.EventRepository;
import com.fruit.maturity.modules.system.entity.AuditLog;
import com.fruit.maturity.modules.system.repo.AuditLogRepository;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EventController {
  private final EventRepository eventRepository;
  private final AuditLogRepository auditRepository;

  public EventController(EventRepository eventRepository, AuditLogRepository auditRepository) {
    this.eventRepository = eventRepository;
    this.auditRepository = auditRepository;
  }

  @GetMapping("/events")
  public ApiResult<List<Event>> list(
      @RequestParam(required = false) Long greenhouseId,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String status) {
    if (greenhouseId != null) {
      return ApiResult.ok(eventRepository.findByGreenhouseId(greenhouseId));
    }
    if (type != null && !type.isBlank()) {
      return ApiResult.ok(eventRepository.findByType(type));
    }
    if (status != null && !status.isBlank()) {
      return ApiResult.ok(eventRepository.findByStatus(status));
    }
    return ApiResult.ok(eventRepository.findAll());
  }

  @GetMapping("/events/page")
  public ApiResult<PageResult<Event>> page(
      @RequestParam(required = false) Long greenhouseId,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
      @RequestParam(required = false) Double minRipeRatio,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<Event> result = eventRepository.findPageByFilter(greenhouseId, type, status, keyword, from, to, minRipeRatio,
      PageRequest.of(page, size));
    return ApiResult.ok(new PageResult<>(result.getContent(), result.getTotalElements()));
  }

  @GetMapping("/events/recent")
  public ApiResult<List<Event>> recent(
      @RequestParam(required = false) Long afterId,
      @RequestParam(defaultValue = "20") int limit) {
    if (afterId == null) return ApiResult.ok(Collections.emptyList());
    int size = Math.min(Math.max(limit, 1), 100);
    return ApiResult.ok(eventRepository.findByIdGreaterThanOrderByIdAsc(afterId, PageRequest.of(0, size)));
  }

  @GetMapping("/events/{id}")
  public ApiResult<Event> detail(@PathVariable Long id) {
    Event event = eventRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found"));
    return ApiResult.ok(event);
  }

  @PostMapping("/events")
  public ApiResult<Event> create(@Valid @RequestBody EventCreateReq req) {
    Event event = new Event();
    event.setGreenhouseId(req.getGreenhouseId());
    event.setType(req.getType());
    event.setSeverity(req.getSeverity());
    event.setStatus(req.getStatus() == null ? "待确认" : req.getStatus());
    event.setImageUrl(req.getImageUrl());
    event.setThumbUrl(req.getThumbUrl() == null ? req.getImageUrl() : req.getThumbUrl());
    event.setAnnotatedUrl(req.getAnnotatedUrl());
    event.setModelVersion(req.getModelVersion());
    event.setConfidence(req.getConfidence());
    event.setRipeRatio(req.getRipeRatio());
    event.setCaptureTime(req.getCaptureTime() == null ? LocalDateTime.now() : req.getCaptureTime());
    return ApiResult.ok(eventRepository.save(event));
  }

  @PatchMapping("/events/{id}/status")
  public ApiResult<Event> updateStatus(@PathVariable Long id, @Valid @RequestBody EventStatusReq req) {
    Event event = eventRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found"));
    event.setStatus(req.getStatus());
    Event saved = eventRepository.save(event);
    writeAudit("event.status:" + id + " -> " + req.getStatus());
    return ApiResult.ok(saved);
  }

  @PatchMapping("/events/{id}/review")
  public ApiResult<Event> updateReview(@PathVariable Long id, @Valid @RequestBody EventReviewReq req) {
    Event event = eventRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found"));
    if (req.getReviewLabel() == null || req.getReviewLabel().isBlank()) {
      event.setReviewLabel(null);
      event.setReviewNote(null);
      event.setReviewedAt(null);
      event.setReviewedBy(null);
    } else {
      event.setReviewLabel(req.getReviewLabel());
      event.setReviewNote(req.getReviewNote());
      event.setReviewedAt(LocalDateTime.now());
      event.setReviewedBy(currentUser());
    }
    Event saved = eventRepository.save(event);
    writeAudit("event.review:" + id + " -> " + (req.getReviewLabel() == null ? "NONE" : req.getReviewLabel()));
    return ApiResult.ok(saved);
  }

  @DeleteMapping("/events/{id}")
  public ApiResult<Void> delete(@PathVariable Long id) {
    if (!eventRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "event not found");
    }
    eventRepository.deleteById(id);
    writeAudit("event.delete:" + id);
    return ApiResult.ok(null);
  }

  private void writeAudit(String action) {
    AuditLog log = new AuditLog();
    log.setActor(currentUser());
    log.setAction(action);
    auditRepository.save(log);
  }

  private String currentUser() {
    if (SecurityContextHolder.getContext().getAuthentication() == null) return "system";
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}

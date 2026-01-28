package com.fruit.maturity.modules.realtime;

import com.fruit.maturity.modules.env.entity.EnvMetric;
import com.fruit.maturity.modules.env.repo.EnvMetricRepository;
import com.fruit.maturity.modules.event.entity.Event;
import com.fruit.maturity.modules.event.repo.EventRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/realtime")
public class RealtimeController {
  private final EventRepository eventRepository;
  private final EnvMetricRepository envRepository;

  public RealtimeController(EventRepository eventRepository, EnvMetricRepository envRepository) {
    this.eventRepository = eventRepository;
    this.envRepository = envRepository;
  }

  @GetMapping(value = "/events/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter streamEvents(@RequestParam(required = false) Long greenhouseId) {
    SseEmitter emitter = new SseEmitter(0L);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    scheduler.scheduleAtFixedRate(() -> {
      try {
        Event payload = greenhouseId == null
          ? eventRepository.findTopByOrderByCreatedAtDesc().orElseGet(this::fakeEvent)
          : eventRepository.findTopByGreenhouseIdOrderByCreatedAtDesc(greenhouseId).orElseGet(this::fakeEvent);
        emitter.send(SseEmitter.event().name("event").data(payload));
      } catch (Exception ex) {
        emitter.completeWithError(ex);
        scheduler.shutdown();
      }
    }, 0, 3, TimeUnit.SECONDS);

    emitter.onCompletion(scheduler::shutdown);
    emitter.onTimeout(() -> {
      emitter.complete();
      scheduler.shutdown();
    });

    return emitter;
  }

  @GetMapping(value = "/env/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter streamEnv(@RequestParam(defaultValue = "1") Long greenhouseId) {
    SseEmitter emitter = new SseEmitter(0L);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    scheduler.scheduleAtFixedRate(() -> {
      try {
        Map<String, Object> payload = new HashMap<>();
        payload.put("greenhouseId", greenhouseId);
        payload.put("ts", LocalDateTime.now());
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("temperature", getLatestMetric(greenhouseId, "temperature", 26.4));
        metrics.put("humidity", getLatestMetric(greenhouseId, "humidity", 62.0));
        metrics.put("co2", getLatestMetric(greenhouseId, "co2", 980.0));
        metrics.put("light", getLatestMetric(greenhouseId, "light", 1.2));
        payload.put("metrics", metrics);

        emitter.send(SseEmitter.event().name("snapshot").data(payload));
      } catch (Exception ex) {
        emitter.completeWithError(ex);
        scheduler.shutdown();
      }
    }, 0, 5, TimeUnit.SECONDS);

    emitter.onCompletion(scheduler::shutdown);
    emitter.onTimeout(() -> {
      emitter.complete();
      scheduler.shutdown();
    });

    return emitter;
  }

  @GetMapping(value = "/vision/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter streamVision(@RequestParam(defaultValue = "1") Long greenhouseId,
      @RequestParam(defaultValue = "1") Long cameraId) {
    SseEmitter emitter = new SseEmitter(0L);
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    scheduler.scheduleAtFixedRate(() -> {
      try {
        Map<String, Object> payload = new HashMap<>();
        payload.put("greenhouseId", greenhouseId);
        payload.put("cameraId", cameraId);
        payload.put("ts", LocalDateTime.now());
        payload.put("boxes", sampleBoxes());
        payload.put("counts", Map.of("ripe", 3, "unripe", 1));
        emitter.send(SseEmitter.event().name("vision").data(payload));
      } catch (Exception ex) {
        emitter.completeWithError(ex);
        scheduler.shutdown();
      }
    }, 0, 200, TimeUnit.MILLISECONDS);

    emitter.onCompletion(scheduler::shutdown);
    emitter.onTimeout(() -> {
      emitter.complete();
      scheduler.shutdown();
    });

    return emitter;
  }

  private Double getLatestMetric(Long greenhouseId, String metric, Double fallback) {
    Optional<EnvMetric> value = envRepository.findTopByGreenhouseIdAndMetricOrderByTsDesc(greenhouseId, metric);
    return value.map(EnvMetric::getValue).orElse(fallback);
  }

  private List<Map<String, Object>> sampleBoxes() {
    return List.of(
      Map.of("x", 0.18, "y", 0.22, "w", 0.32, "h", 0.38, "label", "果实", "score", 0.93, "color", "#22c55e"),
      Map.of("x", 0.58, "y", 0.35, "w", 0.2, "h", 0.26, "label", "果实", "score", 0.88, "color", "#38bdf8")
    );
  }

  private Event fakeEvent() {
    Event event = new Event();
    event.setGreenhouseId(1L);
    event.setType("成熟度偏低");
    event.setSeverity("中");
    event.setStatus("待确认");
    event.setModelVersion("v1.2.0");
    event.setConfidence(0.92);
    event.setRipeRatio(0.46);
    event.setCaptureTime(LocalDateTime.now());
    return event;
  }
}

<template>
  <div class="realtime">
    <StreamStatusBar />

    <div class="main">
      <CameraGrid />
      <div class="right">
        <EnvSnapshotPanel :metrics="metrics" />
        <SideEventFeed :events="events" />
      </div>
    </div>

    <EventQuickCreate />
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import StreamStatusBar from '@/components/realtime/StreamStatusBar.vue';
import CameraGrid from '@/components/realtime/CameraGrid.vue';
import EnvSnapshotPanel from '@/components/realtime/EnvSnapshotPanel.vue';
import SideEventFeed from '@/components/realtime/SideEventFeed.vue';
import EventQuickCreate from '@/components/realtime/EventQuickCreate.vue';
import { overlayBus } from '@/utils/overlayBus';
import { getEventsRecent } from '@/api/events';

type FeedItem = { id: string | number; type: string; time: string };
type MetricItem = { label: string; value: string; status: string };

const events = ref<FeedItem[]>([]);
const metrics = ref<MetricItem[]>([]);

let eventSource: EventSource | null = null;
let envSource: EventSource | null = null;
let visionSource: EventSource | null = null;
let reconnectTimer: number | null = null;
let eventFlushTimer: number | null = null;
let visionFlushTimer: number | null = null;
let lastEventId: number | null = null;
const eventBuffer: FeedItem[] = [];
const visionBuffer = new Map<string, any>();

const buildUrl = (path: string) => {
  const base = import.meta.env.VITE_API_BASE || '/api';
  const normalized = base.endsWith('/') ? base.slice(0, -1) : base;
  if (normalized.startsWith('http')) return `${normalized}${path}`;
  return `${normalized}${path}`;
};

const scheduleEventFlush = () => {
  if (eventFlushTimer) return;
  eventFlushTimer = window.setTimeout(() => {
    eventFlushTimer = null;
    if (!eventBuffer.length) return;
    const pending = eventBuffer.splice(0, eventBuffer.length).reverse();
    events.value = [...pending, ...events.value].slice(0, 200);
  }, 150);
};

const scheduleVisionFlush = () => {
  if (visionFlushTimer) return;
  visionFlushTimer = window.setTimeout(() => {
    visionFlushTimer = null;
    if (!visionBuffer.size) return;
    visionBuffer.forEach(payload => overlayBus.publish(payload.cameraId, payload));
    visionBuffer.clear();
  }, 150);
};

const handleEventMessage = (data: any) => {
  const item = {
    id: data.id || Date.now(),
    type: data.type || '事件',
    time: data.createdAt ? new Date(data.createdAt).toLocaleTimeString() : new Date().toLocaleTimeString(),
  };
  eventBuffer.push(item);
  scheduleEventFlush();
  if (data.id) lastEventId = Number(data.id);
};

const connectEvents = () => {
  eventSource?.close();
  eventSource = new EventSource(buildUrl('/realtime/events/stream?greenhouseId=1'));
  eventSource.addEventListener('event', (evt: MessageEvent) => {
    try {
      const data = JSON.parse(evt.data);
      handleEventMessage(data);
    } catch (err) {
      // ignore parse errors
    }
  });
  eventSource.onerror = () => {
    eventSource?.close();
    if (reconnectTimer) return;
    reconnectTimer = window.setTimeout(async () => {
      reconnectTimer = null;
      connectEvents();
      if (lastEventId) {
        try {
          const res = await getEventsRecent({ afterId: lastEventId, limit: 20 });
          const list = res?.data?.data || [];
          list.forEach(handleEventMessage);
        } catch (err) {
          // ignore
        }
      }
    }, 1500);
  };
};

const connectEnv = () => {
  envSource?.close();
  envSource = new EventSource(buildUrl('/realtime/env/stream?greenhouseId=1'));
  envSource.addEventListener('snapshot', (evt: MessageEvent) => {
    try {
      const data = JSON.parse(evt.data);
      const m = data.metrics || {};
      metrics.value = [
        { label: '温度', value: `${Number(m.temperature || 0).toFixed(1)}℃`, status: Number(m.temperature) > 28 ? 'warn' : 'ok' },
        { label: '湿度', value: `${Number(m.humidity || 0).toFixed(1)}%`, status: Number(m.humidity) > 70 ? 'warn' : 'ok' },
        { label: 'CO₂', value: `${Number(m.co2 || 0).toFixed(0)}ppm`, status: Number(m.co2) > 1200 ? 'warn' : 'ok' },
        { label: '光照', value: `${Number(m.light || 0).toFixed(1)}w/m²`, status: 'ok' },
      ];
    } catch (err) {
      // ignore parse errors
    }
  });
};

const connectVision = () => {
  visionSource?.close();
  visionSource = new EventSource(buildUrl('/realtime/vision/stream?greenhouseId=1'));
  visionSource.addEventListener('vision', (evt: MessageEvent) => {
    try {
      const payload = JSON.parse(evt.data);
      if (!payload || payload.cameraId == null) return;
      visionBuffer.set(String(payload.cameraId), payload);
      scheduleVisionFlush();
    } catch (err) {
      // ignore
    }
  });
};

onMounted(() => {
  connectEvents();
  connectEnv();
  connectVision();
});

onBeforeUnmount(() => {
  eventSource?.close();
  envSource?.close();
  visionSource?.close();
  if (reconnectTimer) window.clearTimeout(reconnectTimer);
  if (eventFlushTimer) window.clearTimeout(eventFlushTimer);
  if (visionFlushTimer) window.clearTimeout(visionFlushTimer);
});
</script>

<style scoped>
.realtime {
  display: grid;
  gap: 14px;
}

.main {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 14px;
}

.right {
  display: grid;
  gap: 14px;
}

@media (max-width: 1100px) {
  .main {
    grid-template-columns: 1fr;
  }
}
</style>


<template>
  <div class="card">
    <div class="title">环境快照</div>
    <div class="row" v-for="m in list" :key="m.label">
      <span class="label">{{ m.label }}</span>
      <strong class="value">{{ m.value }}</strong>
      <span class="status" :class="m.status">{{ m.status }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

type MetricItem = { label: string; value: string; status: string };

const props = defineProps<{ metrics?: MetricItem[] }>();

const defaultMetrics: MetricItem[] = [
  { label: '温度', value: '26.4℃', status: 'ok' },
  { label: '湿度', value: '62%', status: 'ok' },
  { label: 'CO₂', value: '1280ppm', status: 'warn' },
  { label: '光照', value: '1.2w/m²', status: 'ok' },
];

const list = computed(() => (props.metrics && props.metrics.length ? props.metrics : defaultMetrics));
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #0f172a;
  font-weight: 600;
}

.row {
  display: grid;
  grid-template-columns: 1fr auto auto;
  gap: 8px;
  padding: 8px 0;
  font-size: 12px;
  border-bottom: 1px dashed #e2e8f0;
}

.row:last-child {
  border-bottom: none;
}

.label {
  color: #64748b;
}

.value {
  color: #0f172a;
  font-family: var(--font-num);
}

.status {
  padding: 2px 8px;
  border-radius: 999px;
  font-size: 11px;
  text-transform: uppercase;
}

.ok { background: #dcfce7; color: #166534; }
.warn { background: #fef3c7; color: #92400e; }
</style>

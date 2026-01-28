<template>
  <div class="card header">
    <div>
      <h3>事件 #{{ data.id }}</h3>
      <div class="meta">棚号：{{ data.greenhouse }} · 状态：{{ data.status }} · 严重度：{{ data.severity }}</div>
    </div>
    <div class="tags">
      <span class="pill">模型 {{ data.modelVersion }}</span>
      <span class="pill">置信度 {{ data.confidence }}</span>
      <span v-if="data.reviewLabel" class="pill pill--review">{{ data.reviewLabel }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

type EventInfo = {
  id?: number | string;
  greenhouseId?: number;
  status?: string;
  severity?: string;
  modelVersion?: string;
  confidence?: number;
  reviewLabel?: string;
};

const props = defineProps<{ event?: EventInfo }>();

const data = computed(() => ({
  id: props.event?.id ?? 1024,
  greenhouse: props.event?.greenhouseId ? `棚${props.event?.greenhouseId}` : '一号棚',
  status: props.event?.status ?? '处理中',
  severity: props.event?.severity ?? '中',
  modelVersion: props.event?.modelVersion ?? 'v1.2.0',
  confidence: props.event?.confidence ?? 0.93,
  reviewLabel: props.event?.reviewLabel ? (props.event.reviewLabel === 'FALSE_POSITIVE' ? '误检' : props.event.reviewLabel === 'MISS' ? '漏检' : '') : '',
}));
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

h3 {
  margin: 0 0 4px;
  font-size: 16px;
}

.meta {
  font-size: 12px;
  color: #64748b;
}

.tags {
  display: flex;
  gap: 6px;
}

.pill {
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 11px;
  background: #f1f5f9;
  color: #0f172a;
  border: 1px solid #e2e8f0;
}

.pill--review {
  background: #fef3c7;
  color: #92400e;
  border-color: #fcd34d;
}
</style>

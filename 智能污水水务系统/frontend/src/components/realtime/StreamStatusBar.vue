<template>
  <div class="card status-bar">
    <div class="item" v-for="s in stats" :key="s.label">
      <span class="label">{{ s.label }}</span>
      <span class="value">{{ s.value }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
const stats = [
  { label: 'FPS', value: '24' },
  { label: '延迟', value: '180ms' },
  { label: '模型版本', value: 'v1.2.0' },
  { label: '推理', value: 'Running' },
];
</script>

<style scoped>
.status-bar {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
}

.item {
  background: #f8fafc;
  border-radius: 10px;
  padding: 10px;
  text-align: center;
  border: 1px solid #e2e8f0;
}

.label {
  display: block;
  font-size: 11px;
  color: #94a3b8;
}

.value {
  font-size: 14px;
  font-weight: 700;
  font-family: var(--font-num);
  color: #0f172a;
}
</style>

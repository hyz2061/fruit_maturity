<template>
  <div class="card">
    <div class="title">环境快照</div>
    <div class="metric" v-for="m in metrics" :key="m.label">
      <span class="label">{{ m.label }}</span>
      <span class="value">{{ m.value }}</span>
      <span class="spark" :class="m.tone"></span>
    </div>
  </div>
</template>

<script setup lang="ts">
const metrics = [
  { label: '温度', value: '26.4℃', tone: 'tone-blue' },
  { label: '湿度', value: '62%', tone: 'tone-emerald' },
  { label: 'CO₂', value: '950ppm', tone: 'tone-amber' },
  { label: '光照', value: '1.2w/m²', tone: 'tone-slate' },
];
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #0f172a;
  font-weight: 600;
}

.metric {
  display: grid;
  grid-template-columns: 1fr auto 80px;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  padding: 8px 0;
  border-bottom: 1px dashed #e2e8f0;
}

.metric:last-child {
  border-bottom: none;
}

.label {
  color: #475569;
}

.value {
  color: #0f172a;
  font-weight: 600;
  font-family: var(--font-num);
}

.spark {
  height: 6px;
  border-radius: 999px;
  background: #cbd5f5;
}

.tone-blue { background: linear-gradient(90deg, #38bdf8, #1d4ed8); }
.tone-emerald { background: linear-gradient(90deg, #34d399, #0f766e); }
.tone-amber { background: linear-gradient(90deg, #fbbf24, #f97316); }
.tone-slate { background: linear-gradient(90deg, #94a3b8, #475569); }
</style>

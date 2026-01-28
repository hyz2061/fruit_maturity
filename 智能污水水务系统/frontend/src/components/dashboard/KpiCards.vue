<template>
  <div class="kpi-grid">
    <div v-for="item in kpis" :key="item.label" class="kpi-card" :class="item.tone">
      <div class="kpi-head">
        <span class="label">{{ item.label }}</span>
        <span class="badge">{{ item.badge }}</span>
      </div>
      <div class="value">{{ item.value }}</div>
      <div class="hint">{{ item.hint }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
const kpis = [
  { label: '今日事件数', value: 18, hint: '环比 +3', badge: '实时', tone: 'tone-blue' },
  { label: '告警数', value: 6, hint: '处理中 2', badge: '重点', tone: 'tone-amber' },
  { label: '处理完成率', value: '86%', hint: '目标 90%', badge: '效率', tone: 'tone-emerald' },
  { label: '成熟度指数', value: '78', hint: '偏中高', badge: '监测', tone: 'tone-slate' },
];
</script>

<style scoped>
.kpi-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 12px;
}

.kpi-card {
  position: relative;
  border-radius: 16px;
  padding: 14px 16px 16px;
  background: #fff;
  border: 1px solid rgba(226, 232, 240, 0.9);
  box-shadow: 0 10px 24px rgba(15, 23, 42, 0.08);
  overflow: hidden;
}

.kpi-card::before {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(120deg, rgba(14, 116, 144, 0.08), transparent 60%);
  opacity: 0;
  transition: opacity 0.2s ease;
}

.kpi-card:hover::before {
  opacity: 1;
}

.kpi-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.label {
  font-size: 12px;
  color: #64748b;
}

.value {
  font-size: 26px;
  font-family: var(--font-num);
  font-weight: 700;
  margin: 6px 0;
  color: #0b1220;
}

.hint {
  font-size: 12px;
  color: #94a3b8;
}

.badge {
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 999px;
  border: 1px solid rgba(148, 163, 184, 0.4);
  color: #475569;
  background: #f8fafc;
}

.tone-blue { border-left: 3px solid #1d4ed8; }
.tone-amber { border-left: 3px solid #f59e0b; }
.tone-emerald { border-left: 3px solid #0f766e; }
.tone-slate { border-left: 3px solid #475569; }

@media (max-width: 1100px) {
  .kpi-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>

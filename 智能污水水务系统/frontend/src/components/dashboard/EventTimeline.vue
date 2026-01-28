<template>
  <div class="card">
    <div class="title">事件流</div>
    <div class="item" v-for="e in events" :key="e.id">
      <div class="thumb">IMG</div>
      <div class="info">
        <div class="name">{{ e.type }}</div>
        <div class="meta">{{ e.greenhouse }} · {{ e.status }}</div>
      </div>
      <div class="time">{{ e.time }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
const events = [
  { id: 1, type: '成熟度偏低', greenhouse: '一号棚', status: '待确认', time: '10:21' },
  { id: 2, type: '病害疑似', greenhouse: '二号棚', status: '处理中', time: '09:58' },
  { id: 3, type: '成熟度达标', greenhouse: '一号棚', status: '已完成', time: '09:30' },
];
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #0f172a;
  font-weight: 600;
}

.item {
  display: grid;
  grid-template-columns: 48px 1fr auto;
  align-items: center;
  gap: 10px;
  padding: 10px 0;
  border-bottom: 1px dashed #e2e8f0;
}

.item:last-child {
  border-bottom: none;
}

.thumb {
  width: 48px;
  height: 34px;
  border-radius: 8px;
  background: linear-gradient(135deg, #e2e8f0, #cbd5f5);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  color: #64748b;
}

.name {
  font-size: 13px;
  color: #0f172a;
  font-weight: 600;
}

.meta {
  font-size: 11px;
  color: #64748b;
}

.time {
  font-size: 11px;
  color: #94a3b8;
}
</style>

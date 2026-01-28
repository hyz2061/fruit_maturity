<template>
  <div class="card chart">
    <div class="title">环境曲线对比</div>
    <div class="placeholder">事件前后 60min 环境曲线（占位）</div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.chart {
  height: 220px;
  display: grid;
  grid-template-rows: auto 1fr;
  gap: 10px;
}

.title {
  font-size: 14px;
  font-weight: 600;
  color: #0f172a;
}

.placeholder {
  background: repeating-linear-gradient(
    135deg,
    rgba(148, 163, 184, 0.15),
    rgba(148, 163, 184, 0.15) 10px,
    rgba(226, 232, 240, 0.5) 10px,
    rgba(226, 232, 240, 0.5) 20px
  );
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #64748b;
  font-size: 12px;
}
</style>

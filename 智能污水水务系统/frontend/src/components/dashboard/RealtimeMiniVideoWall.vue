<template>
  <div class="card video-wall">
    <div class="header">
      <div>
        <div class="title">实时画面</div>
        <div class="subtitle">当前监控 4 路，在线 4/4</div>
      </div>
      <button class="btn">查看全部</button>
    </div>
    <div class="grid">
      <div v-for="n in 4" :key="n" class="tile">
        <div class="badge">Camera {{ n }}</div>
        <div class="signal">Online</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.video-wall .header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.title {
  font-size: 14px;
  color: #0f172a;
  font-weight: 600;
}

.subtitle {
  font-size: 12px;
  color: #94a3b8;
  margin-top: 2px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 8px;
}

.tile {
  position: relative;
  background: linear-gradient(135deg, #0b1220, #1f2a44);
  color: #e2e8f0;
  border-radius: 12px;
  height: 110px;
  overflow: hidden;
}

.tile::after {
  content: "";
  position: absolute;
  inset: 0;
  background: radial-gradient(120px 60px at 70% 10%, rgba(34, 197, 94, 0.2), transparent 60%);
}

.badge {
  position: absolute;
  left: 10px;
  top: 10px;
  background: rgba(15, 23, 42, 0.7);
  padding: 2px 8px;
  border-radius: 999px;
  font-size: 11px;
}

.signal {
  position: absolute;
  right: 10px;
  bottom: 8px;
  font-size: 11px;
  color: #22c55e;
}
</style>

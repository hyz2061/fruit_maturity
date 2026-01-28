<template>
  <div class="card">
    <div class="title">风险热力图</div>
    <div class="grid">
      <div v-for="(v, i) in blocks" :key="i" :class="['block', v]">
        棚{{ i + 1 }}
      </div>
    </div>
    <div class="legend">
      <span>低</span><i></i><span>高</span>
    </div>
  </div>
</template>

<script setup lang="ts">
const blocks = ['low', 'mid', 'high', 'mid', 'low', 'mid', 'high', 'mid', 'low'];
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #0f172a;
  font-weight: 600;
}

.grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 8px;
}

.block {
  height: 54px;
  border-radius: 10px;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 600;
}

.low { background: #16a34a; }
.mid { background: #f59e0b; }
.high { background: #dc2626; }

.legend {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 11px;
  color: #64748b;
  margin-top: 10px;
}

.legend i {
  width: 80px;
  height: 6px;
  border-radius: 999px;
  background: linear-gradient(90deg, #16a34a, #dc2626);
  display: inline-block;
}
</style>

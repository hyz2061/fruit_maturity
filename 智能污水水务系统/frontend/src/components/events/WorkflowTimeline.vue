<template>
  <div class="card">
    <div class="title">闭环流程</div>
    <div class="steps">
      <div class="step active">新建</div>
      <div class="step">确认</div>
      <div class="step">处理中</div>
      <div class="step">复盘</div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 8px;
  font-weight: 600;
  color: #0f172a;
}

.steps {
  display: grid;
  gap: 6px;
}

.step {
  padding: 6px 10px;
  border-radius: 999px;
  border: 1px solid #e2e8f0;
  font-size: 12px;
  color: #64748b;
}

.step.active {
  background: #e0f2fe;
  color: #0369a1;
  border-color: #7dd3fc;
}
</style>

<template>
  <div class="card">
    <div class="title">可能原因与建议</div>
    <p>温度持续高于阈值，建议加强通风，并复核模型置信度。</p>
    <button class="btn">查看建议</button>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 6px;
  font-weight: 600;
  color: #0f172a;
}

p {
  margin: 0 0 8px;
  font-size: 12px;
  color: #64748b;
}
</style>

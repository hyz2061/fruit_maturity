<template>
  <div class="error">
    <h1>404</h1>
    <p>页面未找到</p>
    <RouterLink class="btn btn--brand" to="/layout/dashboard">返回首页</RouterLink>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.error {
  min-height: 60vh;
  display: grid;
  place-items: center;
  text-align: center;
  gap: 8px;
}

h1 {
  font-size: 48px;
  margin: 0;
}

p {
  margin: 0;
  color: #64748b;
  font-size: 12px;
}
</style>

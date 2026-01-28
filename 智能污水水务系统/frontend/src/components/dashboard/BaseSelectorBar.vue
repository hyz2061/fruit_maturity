<template>
  <div class="card selector-bar">
    <div class="selector-group">
      <div class="field">
        <span>园区</span>
        <select class="select">
          <option>园区A</option>
          <option>园区B</option>
        </select>
      </div>
      <div class="field">
        <span>棚/区域</span>
        <select class="select">
          <option>全部</option>
          <option>一号棚</option>
          <option>二号棚</option>
        </select>
      </div>
      <div class="field">
        <span>时间范围</span>
        <select class="select">
          <option>Today</option>
          <option>24h</option>
          <option>7d</option>
          <option>自定义</option>
        </select>
      </div>
    </div>
    <div class="selector-actions">
      <button class="btn">同步数据</button>
      <button class="btn btn--brand">导出概览</button>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.selector-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}

.selector-group {
  display: flex;
  gap: 12px;
  align-items: center;
  flex-wrap: wrap;
}

.selector-actions {
  display: flex;
  gap: 8px;
}
</style>

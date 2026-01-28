<template>
  <div class="tasks">
    <div class="page-header">
      <div>
        <h2>告警任务看板</h2>
        <p>任务流转与处理效率跟踪</p>
      </div>
      <button class="btn btn--brand">新建任务</button>
    </div>

    <div class="stats">
      <div class="card stat">
        <span>待处理</span>
        <strong>12</strong>
      </div>
      <div class="card stat">
        <span>处理中</span>
        <strong>6</strong>
      </div>
      <div class="card stat">
        <span>已完成</span>
        <strong>58</strong>
      </div>
    </div>

    <div class="board">
      <div class="column">
        <div class="column-title">待处理</div>
        <div class="card task">成熟度偏低 · 一号棚</div>
        <div class="card task">病害疑似 · 二号棚</div>
      </div>
      <div class="column">
        <div class="column-title">处理中</div>
        <div class="card task">光照异常 · 一号棚</div>
      </div>
      <div class="column">
        <div class="column-title">已完成</div>
        <div class="card task">湿度偏高 · 三号棚</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.tasks {
  display: grid;
  gap: 14px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.page-header h2 {
  margin: 0;
  font-size: 18px;
}

.page-header p {
  margin: 4px 0 0;
  font-size: 12px;
  color: #64748b;
}

.stats {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}

.stat {
  display: grid;
  gap: 6px;
  text-align: center;
}

.stat span {
  font-size: 12px;
  color: #64748b;
}

.stat strong {
  font-size: 22px;
  font-family: var(--font-num);
}

.board {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 12px;
}

.column-title {
  font-size: 12px;
  color: #64748b;
  margin-bottom: 8px;
}

.task {
  padding: 10px;
  font-size: 12px;
  border: 1px solid #e2e8f0;
}

@media (max-width: 1100px) {
  .board {
    grid-template-columns: 1fr;
  }
}
</style>

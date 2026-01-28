<template>
  <div class="env-compare">
    <div class="page-header">
      <div>
        <h2>环境对比分析</h2>
        <p>棚区对比、周期对比与相关性洞察</p>
      </div>
      <button class="btn btn--brand">生成对比报告</button>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">对比设置</div>
        <div class="form">
          <label>棚区 A</label>
          <select class="select"><option>一号棚</option></select>
          <label>棚区 B</label>
          <select class="select"><option>二号棚</option></select>
          <label>周期</label>
          <select class="select"><option>本周 vs 上周</option></select>
          <button class="btn">开始对比</button>
        </div>
      </div>
      <div class="card">
        <div class="card-title">相关性分析</div>
        <div class="placeholder">相关性矩阵占位</div>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">分布对比</div>
        <div class="placeholder">箱线图占位</div>
      </div>
      <div class="card">
        <div class="card-title">异常差异</div>
        <ul class="list">
          <li>二号棚夜间湿度高 8%</li>
          <li>一号棚 CO₂ 更稳定</li>
          <li>二号棚光照波动较大</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.env-compare {
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

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.card-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 10px;
}

.form {
  display: grid;
  gap: 8px;
  font-size: 12px;
  color: #64748b;
}

.form label {
  font-size: 11px;
}

.placeholder {
  height: 200px;
  border-radius: 12px;
  background: repeating-linear-gradient(135deg, rgba(148, 163, 184, 0.15), rgba(148, 163, 184, 0.15) 10px, rgba(226, 232, 240, 0.5) 10px, rgba(226, 232, 240, 0.5) 20px);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #64748b;
  font-size: 12px;
}

.list {
  margin: 0;
  padding-left: 16px;
  font-size: 12px;
  color: #64748b;
  display: grid;
  gap: 6px;
}

@media (max-width: 1100px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

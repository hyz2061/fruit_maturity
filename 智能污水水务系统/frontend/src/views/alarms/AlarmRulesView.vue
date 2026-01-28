<template>
  <div class="alarms">
    <div class="page-header">
      <div>
        <h2>告警规则</h2>
        <p>可视化规则引擎与历史回放验证</p>
      </div>
      <button class="btn btn--brand">新建规则</button>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">规则列表</div>
        <ul class="rule-list">
          <li>
            <div>
              <strong>成熟度偏低 + 高温</strong>
              <div class="meta">启用 · 过去 7 天触发 12 次</div>
            </div>
            <span class="tag">运行中</span>
          </li>
          <li>
            <div>
              <strong>病害疑似 + 湿度异常</strong>
              <div class="meta">暂停 · 过去 7 天触发 4 次</div>
            </div>
            <span class="tag off">已停用</span>
          </li>
        </ul>
      </div>
      <div class="card">
        <div class="card-title">规则配置</div>
        <div class="builder">
          <div class="block">视觉事件 = 成熟度偏低</div>
          <div class="block">AND 温度 > 28℃</div>
          <div class="block">AND 置信度 > 0.85</div>
        </div>
        <button class="btn">保存规则</button>
      </div>
    </div>

    <div class="card">
      <div class="card-title">规则回放测试</div>
      <div class="placeholder">历史数据回放占位</div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.alarms {
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

.rule-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: grid;
  gap: 10px;
}

.rule-list li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 10px 12px;
}

.meta {
  font-size: 11px;
  color: #94a3b8;
  margin-top: 2px;
}

.tag {
  font-size: 11px;
  padding: 2px 8px;
  border-radius: 999px;
  background: #dcfce7;
  color: #166534;
}

.tag.off {
  background: #fef3c7;
  color: #92400e;
}

.builder {
  display: grid;
  gap: 8px;
  margin-bottom: 10px;
}

.block {
  background: #f8fafc;
  border: 1px dashed #cbd5f5;
  border-radius: 10px;
  padding: 8px 10px;
  font-size: 12px;
  color: #475569;
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

@media (max-width: 1100px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

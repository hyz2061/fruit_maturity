<template>
  <div class="system">
    <div class="page-header">
      <div>
        <h2>系统管理</h2>
        <p>用户、角色与审计追踪</p>
      </div>
      <button class="btn btn--brand">新增用户</button>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">用户列表</div>
        <table class="table">
          <thead>
            <tr>
              <th>用户名</th>
              <th>角色</th>
              <th>状态</th>
              <th>最近登录</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>admin</td>
              <td>管理员</td>
              <td><span class="pill on">启用</span></td>
              <td>2026-01-22 09:30</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="card">
        <div class="card-title">角色与权限</div>
        <ul class="list">
          <li>管理员：全量权限</li>
          <li>运维：设备/告警/事件</li>
          <li>分析：视觉/环境/报表</li>
        </ul>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">字典配置</div>
        <ul class="list">
          <li>作物类型、病害类型、成熟度等级</li>
          <li>阈值模板、告警级别</li>
        </ul>
      </div>
      <div class="card">
        <div class="card-title">审计日志</div>
        <div class="placeholder">操作审计日志占位</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.system {
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

.table {
  width: 100%;
  border-collapse: collapse;
  font-size: 12px;
}

th, td {
  text-align: left;
  padding: 10px 8px;
  border-bottom: 1px solid #e2e8f0;
}

th {
  color: #64748b;
}

.list {
  margin: 0;
  padding-left: 16px;
  font-size: 12px;
  color: #64748b;
  display: grid;
  gap: 6px;
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

.pill {
  padding: 2px 8px;
  border-radius: 999px;
  background: #f1f5f9;
  font-size: 11px;
}

.pill.on {
  background: #dcfce7;
  color: #166534;
}

@media (max-width: 1100px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

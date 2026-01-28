<template>
  <div class="devices">
    <div class="page-header">
      <div>
        <h2>设备管理</h2>
        <p>摄像头、传感器与网关状态监控</p>
      </div>
      <button class="btn btn--brand">新增设备</button>
    </div>

    <div class="stats">
      <div class="card stat">
        <span>在线设备</span>
        <strong>28</strong>
      </div>
      <div class="card stat">
        <span>离线设备</span>
        <strong>2</strong>
      </div>
      <div class="card stat">
        <span>今日告警</span>
        <strong>4</strong>
      </div>
    </div>

    <div class="card">
      <div class="card-title">设备列表</div>
      <table class="table">
        <thead>
          <tr>
            <th>设备名称</th>
            <th>类型</th>
            <th>棚区</th>
            <th>状态</th>
            <th>心跳</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Camera-01</td>
            <td>摄像头</td>
            <td>一号棚</td>
            <td><span class="pill on">在线</span></td>
            <td>30s</td>
          </tr>
          <tr>
            <td>EnvSensor-02</td>
            <td>传感器</td>
            <td>二号棚</td>
            <td><span class="pill off">离线</span></td>
            <td>--</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.devices {
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

.pill.off {
  background: #fee2e2;
  color: #991b1b;
}
</style>

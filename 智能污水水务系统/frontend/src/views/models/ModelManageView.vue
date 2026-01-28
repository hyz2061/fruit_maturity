<template>
  <div class="models">
    <div class="page-header">
      <div>
        <h2>模型管理</h2>
        <p>版本、评估与灰度发布</p>
      </div>
      <button class="btn btn--brand">上传新模型</button>
    </div>

    <div class="grid">
      <div class="card active">
        <div class="card-title">当前在线模型</div>
        <div class="active-info">
          <div>
            <span>版本</span>
            <strong>v1.2.0</strong>
          </div>
          <div>
            <span>mAP</span>
            <strong>0.81</strong>
          </div>
          <div>
            <span>召回率</span>
            <strong>0.76</strong>
          </div>
        </div>
        <button class="btn">回滚到上一版本</button>
      </div>
      <div class="card">
        <div class="card-title">评估报告</div>
        <div class="placeholder">PR 曲线 / 混淆矩阵占位</div>
      </div>
    </div>

    <div class="card">
      <div class="card-title">版本列表</div>
      <table class="table">
        <thead>
          <tr>
            <th>版本</th>
            <th>上线时间</th>
            <th>mAP</th>
            <th>召回率</th>
            <th>状态</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>v1.2.0</td>
            <td>2026-01-20</td>
            <td>0.81</td>
            <td>0.76</td>
            <td><span class="pill on">在线</span></td>
          </tr>
          <tr>
            <td>v1.1.0</td>
            <td>2026-01-12</td>
            <td>0.78</td>
            <td>0.73</td>
            <td><span class="pill">可回滚</span></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.models {
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

.active {
  border: 1px solid #99f6e4;
  background: linear-gradient(135deg, rgba(14, 116, 144, 0.08), rgba(29, 78, 216, 0.08));
}

.active-info {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  font-size: 12px;
  color: #475569;
  margin-bottom: 10px;
}

.active-info strong {
  display: block;
  font-size: 18px;
  color: #0f172a;
  margin-top: 4px;
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

@media (max-width: 1100px) {
  .grid {
    grid-template-columns: 1fr;
  }
}
</style>

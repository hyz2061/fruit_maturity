<template>
  <div class="reports">
    <div class="page-header">
      <div>
        <h2>报表中心</h2>
        <p>周报、月报与自定义导出</p>
      </div>
      <div class="actions">
        <button class="btn" @click="handleExcel">导出 Excel</button>
        <button class="btn" @click="handlePdf">导出 PDF</button>
        <button class="btn btn--brand">生成报表</button>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">模板选择</div>
        <div class="templates">
          <div class="template">周报</div>
          <div class="template">月报</div>
          <div class="template">自定义</div>
        </div>
      </div>
      <div class="card">
        <div class="card-title">预览</div>
        <div class="placeholder">报表预览占位</div>
      </div>
    </div>

    <div class="card">
      <div class="card-title">历史记录</div>
      <table class="table">
        <thead>
          <tr>
            <th>类型</th>
            <th>周期</th>
            <th>状态</th>
            <th>生成时间</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>周报</td>
            <td>2026-W03</td>
            <td><span class="pill on">完成</span></td>
            <td>2026-01-22</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { exportExcel, exportPdf } from '@/api/reports';

const download = (blob: Blob, filename: string) => {
  const url = window.URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = filename;
  a.click();
  window.URL.revokeObjectURL(url);
};

const handleExcel = async () => {
  const res = await exportExcel();
  download(res.data, 'report.xlsx');
};

const handlePdf = async () => {
  const res = await exportPdf();
  download(res.data, 'report.pdf');
};
</script>

<style scoped>
.reports {
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

.actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
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

.templates {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 8px;
}

.template {
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  padding: 12px;
  text-align: center;
  font-size: 12px;
  color: #475569;
  background: #f8fafc;
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

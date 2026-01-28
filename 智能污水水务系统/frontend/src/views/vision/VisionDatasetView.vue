<template>
  <div class="dataset">
    <div class="page-header">
      <div>
        <h2>样本与数据集</h2>
        <p>高置信样本沉淀与低置信复核队列</p>
      </div>
      <div class="actions">
        <button class="btn">批量导入</button>
        <button class="btn btn--brand">上传样本</button>
      </div>
    </div>

    <div class="grid">
      <div class="card upload">
        <div class="upload-box">拖拽文件或点击上传</div>
        <div class="upload-note">支持 JPG / PNG / JSON 标注</div>
      </div>
      <div class="card">
        <div class="card-title">数据集概览</div>
        <div class="summary">
          <div>
            <span>训练集</span>
            <strong>4,120</strong>
          </div>
          <div>
            <span>验证集</span>
            <strong>1,020</strong>
          </div>
          <div>
            <span>测试集</span>
            <strong>860</strong>
          </div>
        </div>
      </div>
    </div>

    <div class="grid">
      <div class="card">
        <div class="card-title">样本库</div>
        <div class="placeholder">样本画廊占位</div>
      </div>
      <div class="card">
        <div class="card-title">主动学习队列</div>
        <ul class="list">
          <li>低置信样本 #A102 - 待复核</li>
          <li>疑似误报 #B228 - 待标注</li>
          <li>成熟度边缘样本 #C410</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style scoped>
.dataset {
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
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.upload {
  display: grid;
  gap: 8px;
}

.upload-box {
  border: 1px dashed #cbd5f5;
  border-radius: 14px;
  padding: 28px;
  text-align: center;
  color: #64748b;
}

.upload-note {
  font-size: 11px;
  color: #94a3b8;
}

.card-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 10px;
}

.summary {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  font-size: 12px;
  color: #64748b;
}

.summary strong {
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

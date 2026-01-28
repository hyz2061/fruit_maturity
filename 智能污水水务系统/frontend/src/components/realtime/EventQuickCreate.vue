<template>
  <div class="card quick-create">
    <div>
      <div class="title">一键生成事件</div>
      <div class="sub">手动标注可提升训练样本质量</div>
    </div>
    <div class="actions">
      <button class="btn" @click="capture">截图并标记</button>
      <button class="btn btn--brand" @click="create">生成事件</button>
    </div>
    <div v-if="tip" class="tip">{{ tip }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { createEvent } from '@/api/events';

const tip = ref('');

const capture = () => {
  tip.value = '已截图，等待生成事件';
};

const create = async () => {
  await createEvent({
    greenhouseId: 1,
    type: '成熟度偏低',
    severity: '中',
    status: '待确认',
    modelVersion: 'v1.2.0',
    confidence: 0.9,
    ripeRatio: 0.46,
  });
  tip.value = '事件已生成，可在事件中心查看';
};
</script>

<style scoped>
.quick-create {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
  flex-wrap: wrap;
}

.title {
  font-size: 14px;
  font-weight: 600;
  color: #0f172a;
}

.sub {
  font-size: 11px;
  color: #94a3b8;
  margin-top: 2px;
}

.actions {
  display: flex;
  gap: 8px;
}

.tip {
  font-size: 11px;
  color: #0f766e;
}
</style>

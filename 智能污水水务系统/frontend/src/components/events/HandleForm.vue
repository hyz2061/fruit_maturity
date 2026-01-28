<template>
  <div class="card">
    <div class="title">处理记录</div>
    <textarea placeholder="处理措施" rows="3"></textarea>
    <div class="actions">
      <button class="btn">上传照片</button>
      <button class="btn btn--brand">提交</button>
    </div>
    <div class="review">
      <span class="label">复核/纠错</span>
      <div class="buttons">
        <button class="btn" :class="{ active: reviewLabel === 'FALSE_POSITIVE' }" @click="mark('FALSE_POSITIVE')">误检</button>
        <button class="btn" :class="{ active: reviewLabel === 'MISS' }" @click="mark('MISS')">漏检</button>
        <button class="btn ghost" @click="mark('')">撤销</button>
      </div>
    </div>
    <div class="actions admin" v-if="isAdmin">
      <button class="btn" @click="dispatchHarvest">下发采收任务</button>
      <button class="btn btn--brand" @click="closeEvent">关闭事件</button>
    </div>
    <div v-if="tip" class="tip">{{ tip }}</div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { updateEventReview, updateEventStatus } from '@/api/events';
import { useAuthStore } from '@/stores/auth';

const props = defineProps<{ event?: any }>();
const emit = defineEmits(['updated']);

const authStore = useAuthStore();
const isAdmin = computed(() => authStore.isAdmin());
const reviewLabel = ref(props.event?.reviewLabel || '');
const tip = ref('');

watch(() => props.event?.reviewLabel, (val) => {
  reviewLabel.value = val || '';
});

const mark = async (label: string) => {
  if (!props.event?.id) return;
  await updateEventReview(props.event.id, { reviewLabel: label });
  reviewLabel.value = label;
  tip.value = label ? '已标记并进入样本队列' : '已撤销标记';
  emit('updated');
};

const dispatchHarvest = () => {
  tip.value = '采收任务已下发';
};

const closeEvent = async () => {
  if (!props.event?.id) return;
  await updateEventStatus(props.event.id, '已完成');
  tip.value = '事件已关闭';
  emit('updated');
};
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 8px;
  font-weight: 600;
  color: #0f172a;
}

textarea {
  width: 100%;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 10px;
  font-size: 12px;
}

.actions {
  margin-top: 8px;
  display: flex;
  gap: 8px;
}

.review {
  margin-top: 10px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.review .label {
  font-size: 12px;
  color: #64748b;
}

.review .buttons {
  display: flex;
  gap: 8px;
}

.btn.active {
  border-color: #22c55e;
  color: #166534;
}

.btn.ghost {
  background: #fff;
  color: #64748b;
}

.actions.admin {
  justify-content: flex-end;
}

.tip {
  margin-top: 8px;
  font-size: 11px;
  color: #0f766e;
}
</style>

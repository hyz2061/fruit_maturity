<template>
  <div class="card filter">
    <div class="quick">
      <span class="label">快捷筛选</span>
      <div class="chips">
        <button class="chip" @click="applyTodayPending">今日待处理</button>
        <button class="chip" @click="applyHighRipe">成熟占比 > 50%</button>
        <button class="chip" @click="applySpike">一号棚 1h 突增</button>
        <button class="chip ghost" @click="resetAll">重置</button>
      </div>
    </div>
    <div class="fields">
      <div class="field">
        <span>类型</span>
        <select class="select" v-model="filters.type">
          <option value="">全部</option>
          <option value="成熟度偏低">成熟度偏低</option>
          <option value="成熟度达标">成熟度达标</option>
          <option value="病害疑似">病害疑似</option>
        </select>
      </div>
      <div class="field">
        <span>棚号</span>
        <select class="select" v-model="filters.greenhouseId">
          <option value="">全部</option>
          <option value="1">一号棚</option>
          <option value="2">二号棚</option>
        </select>
      </div>
      <div class="field">
        <span>状态</span>
        <select class="select" v-model="filters.status">
          <option value="">全部</option>
          <option value="待确认">待确认</option>
          <option value="处理中">处理中</option>
          <option value="已完成">已完成</option>
        </select>
      </div>
      <div class="search">
        <input type="text" v-model="filters.keyword" placeholder="事件ID / 关键词" />
        <button class="btn" @click="$emit('search')">查询</button>
      </div>
      <button class="btn btn--brand" @click="$emit('create')">新建事件</button>
    </div>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{ filters: any }>();

const emit = defineEmits(['create', 'search']);

const pad = (n: number) => String(n).padStart(2, '0');
const toLocalIso = (d: Date) => {
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`;
};

const applyTodayPending = () => {
  const now = new Date();
  const start = new Date();
  start.setHours(0, 0, 0, 0);
  props.filters.status = '待确认';
  props.filters.from = toLocalIso(start);
  props.filters.to = toLocalIso(now);
  props.filters.minRipeRatio = '';
  props.filters.type = '';
  props.filters.keyword = '';
  emit('search');
};

const applyHighRipe = () => {
  const now = new Date();
  const start = new Date();
  start.setHours(0, 0, 0, 0);
  props.filters.minRipeRatio = '0.5';
  props.filters.from = toLocalIso(start);
  props.filters.to = toLocalIso(now);
  emit('search');
};

const applySpike = () => {
  const now = new Date();
  const start = new Date(now.getTime() - 60 * 60 * 1000);
  props.filters.greenhouseId = '1';
  props.filters.from = toLocalIso(start);
  props.filters.to = toLocalIso(now);
  emit('search');
};

const resetAll = () => {
  props.filters.greenhouseId = '';
  props.filters.type = '';
  props.filters.status = '';
  props.filters.keyword = '';
  props.filters.from = '';
  props.filters.to = '';
  props.filters.minRipeRatio = '';
  emit('search');
};
</script>

<style scoped>
.filter {
  display: grid;
  gap: 12px;
}

.fields {
  display: flex;
  gap: 12px;
  align-items: flex-end;
  flex-wrap: wrap;
}

.quick {
  display: flex;
  align-items: center;
  gap: 10px;
}

.quick .label {
  font-size: 12px;
  color: #64748b;
}

.chips {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.chip {
  padding: 6px 10px;
  font-size: 11px;
  border-radius: 999px;
  border: 1px solid #e2e8f0;
  background: #f8fafc;
  color: #0f172a;
  cursor: pointer;
}

.chip.ghost {
  background: #fff;
  color: #64748b;
}

.search {
  display: flex;
  align-items: center;
  gap: 6px;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 4px 8px;
  background: #fff;
}

.search input {
  border: none;
  outline: none;
  font-size: 13px;
}
</style>

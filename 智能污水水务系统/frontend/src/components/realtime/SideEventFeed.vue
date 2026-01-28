<template>
  <div class="card">
    <div class="title">实时事件流</div>
    <VirtualList :items="list" :height="260" :item-height="34">
      <template #default="{ item }">
        <div class="item">
          <span class="name">{{ item.type }}</span>
          <span class="meta">{{ item.time }}</span>
        </div>
      </template>
    </VirtualList>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import VirtualList from '@/components/common/VirtualList.vue';

type FeedItem = { id: string | number; type: string; time: string };

const props = defineProps<{ events?: FeedItem[] }>();

const defaultEvents: FeedItem[] = [
  { id: 1, type: '成熟度偏低', time: '10:21' },
  { id: 2, type: '病害疑似', time: '10:18' },
  { id: 3, type: '成熟度达标', time: '10:15' },
];

const list = computed(() => (props.events && props.events.length ? props.events : defaultEvents));
</script>

<style scoped>
.title {
  font-size: 14px;
  margin-bottom: 10px;
  color: #0f172a;
  font-weight: 600;
}

.item {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px dashed #e2e8f0;
  font-size: 12px;
  height: 34px;
  box-sizing: border-box;
}

.item:last-child {
  border-bottom: none;
}

.name {
  font-weight: 600;
  color: #0f172a;
}

.meta {
  color: #94a3b8;
}
</style>

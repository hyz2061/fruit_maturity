<template>
  <div class="pagination">
    <button class="btn" :disabled="page<=1" @click="$emit('change', page-1)">上一页</button>
    <span>{{ page }} / {{ pageCount }}</span>
    <button class="btn" :disabled="page>=pageCount" @click="$emit('change', page+1)">下一页</button>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';

const props = defineProps<{ page: number; total: number; size: number }>();

defineEmits(['change']);

const pageCount = computed(() => Math.max(1, Math.ceil(props.total / props.size)));
</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  color: #64748b;
  font-size: 12px;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>

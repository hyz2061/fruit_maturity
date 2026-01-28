<template>
  <div class="card compare">
    <div class="panel">
      <div class="caption">原图</div>
      <div class="img">
        <LazyImage :src="originUrl" />
      </div>
    </div>
    <div class="panel">
      <div class="caption">标注图</div>
      <div class="img">
        <LazyImage :src="annotatedUrl" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import LazyImage from '@/components/common/LazyImage.vue';

const props = defineProps<{ originUrl?: string; annotatedUrl?: string }>();

const originUrl = computed(() => props.originUrl || '');
const annotatedUrl = computed(() => props.annotatedUrl || props.originUrl || '');
</script>

<style scoped>
.compare {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;
}

.panel {
  display: grid;
  gap: 6px;
}

.caption {
  font-size: 12px;
  color: #64748b;
}

.img {
  border-radius: 12px;
  height: 200px;
  overflow: hidden;
}
</style>

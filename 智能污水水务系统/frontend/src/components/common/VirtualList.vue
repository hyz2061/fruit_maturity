<template>
  <div ref="container" class="virtual-list" :style="heightStyle" @scroll="onScroll">
    <div class="spacer" :style="{ height: `${totalHeight}px` }"></div>
    <div class="items" :style="{ transform: `translateY(${offset}px)` }">
      <slot v-for="(item, idx) in visibleItems" :key="start + idx" :item="item" :index="start + idx" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';

const props = withDefaults(defineProps<{
  items: any[];
  itemHeight?: number;
  height?: number;
  overscan?: number;
}>(), {
  itemHeight: 36,
  overscan: 4,
});

const container = ref<HTMLElement | null>(null);
const scrollTop = ref(0);
const viewportHeight = ref(props.height || 0);
let resizeObserver: ResizeObserver | null = null;

const heightStyle = computed(() => (
  props.height ? { height: `${props.height}px` } : undefined
));

const totalHeight = computed(() => props.items.length * props.itemHeight);
const start = computed(() => Math.max(0, Math.floor(scrollTop.value / props.itemHeight) - props.overscan));
const visibleCount = computed(() => Math.ceil(viewportHeight.value / props.itemHeight) + props.overscan * 2);
const end = computed(() => Math.min(props.items.length, start.value + visibleCount.value));
const visibleItems = computed(() => props.items.slice(start.value, end.value));
const offset = computed(() => start.value * props.itemHeight);

const onScroll = () => {
  if (!container.value) return;
  scrollTop.value = container.value.scrollTop;
};

onMounted(() => {
  if (props.height) return;
  const el = container.value;
  if (!el) return;
  const update = () => {
    viewportHeight.value = el.clientHeight;
  };
  update();
  resizeObserver = new ResizeObserver(update);
  resizeObserver.observe(el);
});

onBeforeUnmount(() => {
  resizeObserver?.disconnect();
});
</script>

<style scoped>
.virtual-list {
  position: relative;
  overflow: auto;
}

.spacer {
  width: 100%;
}

.items {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
}
</style>

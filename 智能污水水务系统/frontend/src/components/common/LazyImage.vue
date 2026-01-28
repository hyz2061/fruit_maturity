<template>
  <div ref="rootRef" class="lazy" :class="{ loaded }">
    <img
      v-if="currentSrc"
      :src="currentSrc"
      :alt="alt"
      :class="imgClass"
      @load="loaded = true"
      @error="handleError"
      loading="lazy"
    />
    <div v-else class="placeholder">
      <slot name="placeholder">IMG</slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref } from 'vue';

const props = withDefaults(defineProps<{
  src?: string;
  alt?: string;
  placeholder?: string;
  imgClass?: string;
}>(), {
  alt: '',
});

const visible = ref(false);
const loaded = ref(false);
const failed = ref(false);
let observer: IntersectionObserver | null = null;
const rootRef = ref<HTMLElement | null>(null);
const imgClass = computed(() => props.imgClass || '');

const currentSrc = computed(() => {
  if (!visible.value || failed.value) return props.placeholder || '';
  return props.src || props.placeholder || '';
});

const handleError = () => {
  failed.value = true;
};

onMounted(() => {
  if (!('IntersectionObserver' in window)) {
    visible.value = true;
    return;
  }
  observer = new IntersectionObserver(entries => {
    const entry = entries[0];
    if (entry?.isIntersecting) {
      visible.value = true;
      observer?.disconnect();
      observer = null;
    }
  });
  const el = rootRef.value;
  if (el) observer.observe(el);
});

onBeforeUnmount(() => {
  observer?.disconnect();
});
</script>

<style scoped>
.lazy {
  position: relative;
  width: 100%;
  height: 100%;
  display: block;
}

img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  background: linear-gradient(135deg, #e2e8f0, #cbd5f5);
  color: #64748b;
  font-size: 10px;
}
</style>

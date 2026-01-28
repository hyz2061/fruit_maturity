<template>
  <div class="video">
    <div class="label">{{ label }}</div>
    <div class="status">{{ src ? '在线' : '未配置' }}</div>
    <div class="canvas">
      <video ref="videoRef" :poster="poster" autoplay muted playsinline></video>
      <div v-if="!src" class="empty">未绑定视频流地址</div>
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref, watch } from 'vue';
import Hls from 'hls.js';

const props = defineProps<{ label: string; src?: string; poster?: string }>();
const videoRef = ref<HTMLVideoElement | null>(null);
let hls: Hls | null = null;

const attach = (url?: string) => {
  if (!videoRef.value) return;
  if (!url) return;

  if (Hls.isSupported() && url.endsWith('.m3u8')) {
    hls?.destroy();
    hls = new Hls({ lowLatencyMode: true });
    hls.loadSource(url);
    hls.attachMedia(videoRef.value);
  } else {
    videoRef.value.src = url;
  }
};

onMounted(() => {
  attach(props.src);
});

watch(() => props.src, (val) => {
  attach(val);
});

onBeforeUnmount(() => {
  hls?.destroy();
  hls = null;
});
</script>

<style scoped>
.video {
  position: relative;
  background: linear-gradient(135deg, #0b1220, #23314f);
  border-radius: 12px;
  height: 200px;
  overflow: hidden;
  border: 1px solid rgba(148, 163, 184, 0.2);
}

.label {
  position: absolute;
  left: 8px;
  top: 8px;
  font-size: 11px;
  color: #e2e8f0;
  background: rgba(15, 23, 42, 0.7);
  padding: 2px 8px;
  border-radius: 999px;
  z-index: 2;
}

.status {
  position: absolute;
  right: 8px;
  top: 8px;
  font-size: 11px;
  color: #22c55e;
  z-index: 2;
}

.canvas {
  position: absolute;
  inset: 0;
}

video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.empty {
  position: absolute;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #94a3b8;
  font-size: 12px;
  background: rgba(15, 23, 42, 0.5);
}
</style>

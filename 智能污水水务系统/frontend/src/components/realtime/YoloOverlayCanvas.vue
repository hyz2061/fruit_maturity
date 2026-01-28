<template>
  <canvas ref="canvasRef" class="overlay"></canvas>
</template>

<script setup lang="ts">
import { onBeforeUnmount, onMounted, ref } from 'vue';
import type { OverlayPayload } from '@/utils/overlayBus';
import { overlayBus } from '@/utils/overlayBus';

const props = defineProps<{ cameraId: string | number }>();

const canvasRef = ref<HTMLCanvasElement | null>(null);
let ctx: CanvasRenderingContext2D | null = null;
let rafId = 0;
let pending: OverlayPayload | null = null;
let resizeObserver: ResizeObserver | null = null;
let unsubscribe: (() => void) | null = null;

const scheduleDraw = (payload: OverlayPayload) => {
  pending = payload;
  if (rafId) return;
  rafId = requestAnimationFrame(() => {
    rafId = 0;
    if (pending) draw(pending);
    pending = null;
  });
};

const setupCanvas = () => {
  const canvas = canvasRef.value;
  if (!canvas) return;
  ctx = canvas.getContext('2d');
  const resize = () => {
    const rect = canvas.getBoundingClientRect();
    const dpr = window.devicePixelRatio || 1;
    canvas.width = Math.floor(rect.width * dpr);
    canvas.height = Math.floor(rect.height * dpr);
    if (ctx) ctx.setTransform(dpr, 0, 0, dpr, 0, 0);
  };
  resize();
  resizeObserver = new ResizeObserver(resize);
  resizeObserver.observe(canvas);
};

const draw = (payload: OverlayPayload) => {
  const canvas = canvasRef.value;
  if (!canvas || !ctx) return;
  const width = canvas.clientWidth;
  const height = canvas.clientHeight;
  ctx.clearRect(0, 0, width, height);
  ctx.font = '12px "Inter", system-ui, sans-serif';
  ctx.textBaseline = 'top';

  const counts = payload.counts || {};
  const countKeys = Object.keys(counts);
  if (countKeys.length) {
    const text = countKeys.map(k => `${k}:${counts[k]}`).join('  ');
    const padding = 6;
    const metrics = ctx.measureText(text);
    const boxW = metrics.width + padding * 2;
    const boxH = 18;
    ctx.fillStyle = 'rgba(15, 23, 42, 0.6)';
    ctx.fillRect(8, 8, boxW, boxH);
    ctx.fillStyle = '#e2e8f0';
    ctx.fillText(text, 8 + padding, 8 + 2);
  }

  payload.boxes.forEach(box => {
    const useNormalized = box.w <= 1 && box.h <= 1 && box.x <= 1 && box.y <= 1;
    const x = useNormalized ? box.x * width : box.x;
    const y = useNormalized ? box.y * height : box.y;
    const w = useNormalized ? box.w * width : box.w;
    const h = useNormalized ? box.h * height : box.h;
    const color = box.color || '#22c55e';
    ctx.strokeStyle = color;
    ctx.lineWidth = 2;
    ctx.strokeRect(x, y, w, h);

    const label = box.label ? `${box.label}${box.score != null ? ` ${box.score.toFixed(2)}` : ''}` : '';
    if (label) {
      const padding = 4;
      const textW = ctx.measureText(label).width;
      const textH = 16;
      const tagX = x;
      const tagY = Math.max(0, y - textH - 4);
      ctx.fillStyle = 'rgba(15, 23, 42, 0.6)';
      ctx.fillRect(tagX, tagY, textW + padding * 2, textH);
      ctx.fillStyle = color;
      ctx.fillText(label, tagX + padding, tagY + 2);
    }
  });
};

onMounted(() => {
  setupCanvas();
  unsubscribe = overlayBus.subscribe(props.cameraId, scheduleDraw);
});

onBeforeUnmount(() => {
  if (unsubscribe) unsubscribe();
  if (rafId) cancelAnimationFrame(rafId);
  resizeObserver?.disconnect();
});
</script>

<style scoped>
.overlay {
  position: absolute;
  inset: 0;
  pointer-events: none;
}
</style>

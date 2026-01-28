<template>
  <div class="card camera-wall">
    <div class="header">
      <div>
        <div class="title">摄像头墙</div>
        <div class="sub">支持 1 / 4 / 9 画面切换</div>
      </div>
      <div class="modes">
        <button class="btn">1</button>
        <button class="btn">4</button>
        <button class="btn">9</button>
      </div>
    </div>
    <div class="grid">
      <VideoPlayer
        v-for="cam in list"
        :key="cam.id"
        :label="cam.name"
        :src="cam.streamUrl"
      >
        <YoloOverlayCanvas :camera-id="cam.id" />
      </VideoPlayer>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import VideoPlayer from './VideoPlayer.vue';
import YoloOverlayCanvas from './YoloOverlayCanvas.vue';
import { getDevices } from '@/api/devices';

const cameras = ref<any[]>([]);

const fallback = [
  { id: 1, name: 'Camera 1', streamUrl: '' },
  { id: 2, name: 'Camera 2', streamUrl: '' },
  { id: 3, name: 'Camera 3', streamUrl: '' },
  { id: 4, name: 'Camera 4', streamUrl: '' },
];

const list = computed(() => (cameras.value.length ? cameras.value : fallback));

const load = async () => {
  try {
    const res = await getDevices({ type: 'camera' });
    cameras.value = res?.data?.data || [];
  } catch (e) {
    cameras.value = [];
  }
};

onMounted(load);
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
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

.modes .btn {
  margin-left: 4px;
  padding: 4px 8px;
}

.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 8px;
}
</style>

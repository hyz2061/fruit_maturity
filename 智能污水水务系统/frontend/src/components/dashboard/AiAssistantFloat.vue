<template>
  <button class="ai-float" @click="store.setOpen(true)">智能问答</button>
</template>

<script setup lang="ts">
import { useAiStore } from '@/stores/ai';

const store = useAiStore();
</script>

<style scoped>
.ai-float {
  position: fixed;
  right: 24px;
  bottom: 24px;
  background: linear-gradient(135deg, #0f766e, #1d4ed8);
  color: #fff;
  border: none;
  padding: 10px 18px;
  border-radius: 999px;
  box-shadow: 0 14px 30px rgba(14, 116, 144, 0.35);
  cursor: pointer;
  font-size: 13px;
}
</style>

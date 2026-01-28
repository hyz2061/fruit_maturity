<template>
  <div v-if="store.open" class="ai-overlay" @click="store.setOpen(false)"></div>
  <aside class="ai-drawer" :class="{ open: store.open }">
    <header>
      <div>
        <h3>智能问答</h3>
        <p>面向运营与比赛展示的洞察助手</p>
      </div>
      <button class="btn" @click="store.setOpen(false)">关闭</button>
    </header>

    <section class="messages">
      <div v-if="!store.messages.length" class="empty">
        请输入问题，例如“当前成熟度偏低原因？”
      </div>
      <div v-for="msg in store.messages" :key="msg.id" class="msg" :class="msg.role">
        <div class="bubble">
          {{ msg.content }}
        </div>
        <div class="time">{{ msg.time }}</div>
      </div>
      <div v-if="store.loading" class="msg assistant">
        <div class="bubble">正在生成分析...</div>
      </div>
    </section>

    <footer>
      <textarea v-model="input" placeholder="输入问题，支持成熟度/告警/模型/环境指标" rows="2"></textarea>
      <div class="actions">
        <button class="btn" @click="store.clear()">清空</button>
        <button class="btn btn--brand" @click="send">发送</button>
      </div>
    </footer>
  </aside>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { chatAi } from '@/api/ai';
import { useAiStore } from '@/stores/ai';

const store = useAiStore();
const input = ref('');

const send = async () => {
  const text = input.value.trim();
  if (!text || store.loading) return;

  const now = new Date().toLocaleTimeString();
  store.addMessage({ id: `u-${Date.now()}`, role: 'user', content: text, time: now });
  input.value = '';
  store.setLoading(true);

  try {
    const res = await chatAi(text);
    const answer = res?.data?.data?.answer || '已收到问题，稍后给出分析。';
    store.addMessage({ id: `a-${Date.now()}`, role: 'assistant', content: answer, time: new Date().toLocaleTimeString() });
  } catch (err) {
    store.addMessage({ id: `a-${Date.now()}`, role: 'assistant', content: '服务暂不可用，请稍后重试。', time: new Date().toLocaleTimeString() });
  } finally {
    store.setLoading(false);
  }
};
</script>

<style scoped>
.ai-overlay {
  position: fixed;
  inset: 0;
  background: rgba(15, 23, 42, 0.35);
  z-index: 40;
}

.ai-drawer {
  position: fixed;
  top: 0;
  right: 0;
  width: 360px;
  max-width: 90vw;
  height: 100vh;
  background: #ffffff;
  box-shadow: -20px 0 40px rgba(15, 23, 42, 0.15);
  transform: translateX(100%);
  transition: transform 0.25s ease;
  z-index: 50;
  display: grid;
  grid-template-rows: auto 1fr auto;
  border-left: 1px solid #e2e8f0;
}

.ai-drawer.open {
  transform: translateX(0);
}

header {
  padding: 16px;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

header h3 {
  margin: 0 0 4px;
  font-size: 16px;
}

header p {
  margin: 0;
  font-size: 11px;
  color: #94a3b8;
}

.messages {
  padding: 16px;
  overflow-y: auto;
  display: grid;
  gap: 10px;
}

.empty {
  font-size: 12px;
  color: #94a3b8;
  text-align: center;
  margin-top: 20px;
}

.msg {
  display: grid;
  gap: 4px;
}

.msg.user {
  justify-items: end;
}

.msg.assistant {
  justify-items: start;
}

.bubble {
  max-width: 85%;
  padding: 8px 10px;
  border-radius: 12px;
  font-size: 12px;
  line-height: 1.5;
  background: #f1f5f9;
}

.msg.user .bubble {
  background: linear-gradient(135deg, #0f766e, #1d4ed8);
  color: #fff;
}

.time {
  font-size: 10px;
  color: #94a3b8;
}

footer {
  border-top: 1px solid #e2e8f0;
  padding: 12px;
  display: grid;
  gap: 8px;
}

textarea {
  resize: none;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 8px;
  font-size: 12px;
}

.actions {
  display: flex;
  justify-content: space-between;
  gap: 8px;
}
</style>

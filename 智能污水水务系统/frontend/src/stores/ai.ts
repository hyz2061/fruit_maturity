import { defineStore } from 'pinia';

export interface AiMessage {
  id: string;
  role: 'user' | 'assistant';
  content: string;
  time: string;
}

export const useAiStore = defineStore('ai', {
  state: () => ({
    open: false,
    loading: false,
    messages: [] as AiMessage[],
  }),
  actions: {
    setOpen(value: boolean) {
      this.open = value;
    },
    addMessage(message: AiMessage) {
      this.messages.push(message);
    },
    setLoading(value: boolean) {
      this.loading = value;
    },
    clear() {
      this.messages = [];
    },
  },
});

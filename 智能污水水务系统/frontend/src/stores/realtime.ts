import { defineStore } from 'pinia';

export const useRealtimeStore = defineStore('realtime', {
  state: () => ({
    streams: [] as any[],
    inferenceStatus: 'idle',
    eventFeed: [] as any[],
  }),
});

import { defineStore } from 'pinia';

export const useEventStore = defineStore('event', {
  state: () => ({
    list: [] as any[],
    detail: null as any,
  }),
});

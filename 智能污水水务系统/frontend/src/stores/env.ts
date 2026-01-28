import { defineStore } from 'pinia';

export const useEnvStore = defineStore('env', {
  state: () => ({
    metrics: [] as any[],
    quality: null as any,
  }),
});

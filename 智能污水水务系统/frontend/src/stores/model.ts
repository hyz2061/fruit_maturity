import { defineStore } from 'pinia';

export const useModelStore = defineStore('model', {
  state: () => ({
    versions: [] as any[],
    activeId: null as null | number,
  }),
});

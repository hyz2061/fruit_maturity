import { defineStore } from 'pinia';

export const useAppStore = defineStore('app', {
  state: () => ({
    currentParkId: null as null | number,
    currentGreenhouseId: null as null | number,
    timeRange: 'Today',
    theme: 'light',
    layout: 'side',
  }),
});

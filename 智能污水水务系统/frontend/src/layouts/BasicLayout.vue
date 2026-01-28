<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand__mark"></div>
        <div>
          <div class="brand__title">果实成熟度监测</div>
          <div class="brand__sub">Agritech Operations Center</div>
        </div>
      </div>
      <nav class="menu">
        <RouterLink v-for="item in menuList" :key="item.path" :to="item.path">{{ item.name }}</RouterLink>
      </nav>
    </aside>

    <div class="content">
      <header class="topbar">
        <div class="topbar__left">
          <div class="field">
            <span>园区</span>
            <select class="select">
              <option>园区选择</option>
              <option>园区A</option>
              <option>园区B</option>
            </select>
          </div>
          <div class="field">
            <span>棚/区域</span>
            <select class="select">
              <option>棚/区域选择</option>
              <option>一号棚</option>
              <option>二号棚</option>
            </select>
          </div>
          <div class="field">
            <span>时间范围</span>
            <div class="chip-group">
              <button class="active">Today</button>
              <button>24h</button>
              <button>7d</button>
              <button>自定义</button>
            </div>
          </div>
        </div>
        <div class="topbar__right">
          <div class="search">
            <input type="text" placeholder="事件ID / 棚号 / 病害 / 成熟类目" />
            <button class="btn">搜索</button>
          </div>
          <button class="btn btn--brand" @click="aiStore.setOpen(true)">智能问答</button>
        </div>
      </header>

      <main class="page">
        <router-view />
      </main>
    </div>

    <AiChatDrawer />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import AiChatDrawer from '@/components/ai/AiChatDrawer.vue';
import { useAiStore } from '@/stores/ai';
import { useAuthStore } from '@/stores/auth';

const aiStore = useAiStore();
const authStore = useAuthStore();

const menus = [
  { name: '总览大屏', path: '/layout/dashboard' },
  { name: '实时监控', path: '/layout/realtime' },
  { name: '事件中心', path: '/layout/events' },
  { name: '环境数据', path: '/layout/env/overview' },
  { name: '视觉分析', path: '/layout/vision/stat' },
  { name: '告警与任务', path: '/layout/alarms/rules' },
  { name: '模型管理', path: '/layout/models', roles: ['ROLE_ADMIN'] },
  { name: '设备管理', path: '/layout/devices' },
  { name: '报表中心', path: '/layout/reports' },
  { name: '系统管理', path: '/layout/system', roles: ['ROLE_ADMIN'] },
];

const menuList = computed(() => menus.filter(item =>
  !item.roles || item.roles.some(role => authStore.roles.includes(role))
));
</script>

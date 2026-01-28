import type { RouteRecordRaw } from 'vue-router';
import BasicLayout from '@/layouts/BasicLayout.vue';

export const routes: RouteRecordRaw[] = [
  { path: '/', redirect: '/layout/dashboard' },
  { path: '/login', name: 'Login', component: () => import('@/views/login/LoginView.vue'), meta: { title: '登录' } },
  { path: '/register', name: 'Register', component: () => import('@/views/register/RegisterView.vue'), meta: { title: '注册' } },
  {
    path: '/layout',
    name: 'Layout',
    component: BasicLayout,
    redirect: '/layout/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('@/views/dashboard/DashboardView.vue'), meta: { title: '总览大屏' } },
      { path: 'realtime', name: 'Realtime', component: () => import('@/views/realtime/RealtimeView.vue'), meta: { title: '实时监控' } },

      { path: 'events', name: 'Events', component: () => import('@/views/events/EventsView.vue'), meta: { title: '事件中心' } },
      { path: 'events/:id', name: 'EventDetail', component: () => import('@/views/events/EventDetailView.vue'), props: true, meta: { title: '事件详情', hidden: true, activeMenu: '/layout/events' } },

      { path: 'env', redirect: '/layout/env/overview', meta: { title: '环境数据' } },
      { path: 'env/overview', name: 'EnvOverview', component: () => import('@/views/env/EnvOverviewView.vue'), meta: { title: '环境概览' } },
      { path: 'env/compare', name: 'EnvCompare', component: () => import('@/views/env/EnvCompareView.vue'), meta: { title: '环境对比' } },

      { path: 'vision', redirect: '/layout/vision/stat', meta: { title: '视觉分析' } },
      { path: 'vision/stat', name: 'VisionStat', component: () => import('@/views/vision/VisionStatView.vue'), meta: { title: '视觉统计' } },
      { path: 'vision/dataset', name: 'VisionDataset', component: () => import('@/views/vision/VisionDatasetView.vue'), meta: { title: '样本库' } },

      { path: 'alarms', redirect: '/layout/alarms/rules', meta: { title: '告警与任务' } },
      { path: 'alarms/rules', name: 'AlarmRules', component: () => import('@/views/alarms/AlarmRulesView.vue'), meta: { title: '告警规则' } },
      { path: 'alarms/tasks', name: 'AlarmTasks', component: () => import('@/views/alarms/TasksView.vue'), meta: { title: '告警任务' } },

      { path: 'models', name: 'Models', component: () => import('@/views/models/ModelManageView.vue'), meta: { title: '模型管理', roles: ['ROLE_ADMIN'] } },
      { path: 'devices', name: 'Devices', component: () => import('@/views/devices/DeviceManageView.vue'), meta: { title: '设备管理' } },
      { path: 'reports', name: 'Reports', component: () => import('@/views/reports/ReportsView.vue'), meta: { title: '报表中心' } },
      { path: 'system', name: 'System', component: () => import('@/views/system/SystemView.vue'), meta: { title: '系统管理', roles: ['ROLE_ADMIN'] } },
    ],
  },
  { path: '/403', name: '403', component: () => import('@/views/errors/403.vue'), meta: { title: '无权限' } },
  { path: '/404', name: '404', component: () => import('@/views/errors/404.vue'), meta: { title: '未找到' } },
  { path: '/:pathMatch(.*)*', redirect: '/404' },
];

import { createRouter, createWebHistory } from 'vue-router';
import { routes } from './routes';
import { useAuthStore } from '@/stores/auth';
import { getMe } from '@/api/auth';

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 }),
});

router.beforeEach(async (to, _from, next) => {
  const token = localStorage.getItem('token');
  if (!token && !['/login', '/register'].includes(to.path)) {
    next('/login');
    return;
  }

  if (token) {
    const store = useAuthStore();
    if (!store.user || store.roles.length === 0) {
      try {
        const res = await getMe();
        const data = res?.data?.data;
        if (data?.username) {
          store.setProfile({ username: data.username }, data.roles || []);
        }
      } catch (e) {
        // ignore
      }
    }

    const roles = (to.meta?.roles as string[]) || [];
    if (roles.length && !roles.some(role => store.roles.includes(role))) {
      next('/403');
      return;
    }
  }

  next();
});

export default router;

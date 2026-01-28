import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    user: (localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user') as string) : null) as null | { username: string },
    roles: (localStorage.getItem('roles') ? JSON.parse(localStorage.getItem('roles') as string) : []) as string[],
    permissions: [] as string[],
  }),
  actions: {
    setToken(token: string) {
      this.token = token;
      localStorage.setItem('token', token);
    },
    setProfile(user: { username: string }, roles: string[]) {
      this.user = user;
      this.roles = roles;
      localStorage.setItem('user', JSON.stringify(user));
      localStorage.setItem('roles', JSON.stringify(roles));
    },
    clear() {
      this.token = '';
      this.user = null;
      this.roles = [];
      this.permissions = [];
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('roles');
    },
    isAdmin() {
      return this.roles.includes('ROLE_ADMIN');
    },
  },
});

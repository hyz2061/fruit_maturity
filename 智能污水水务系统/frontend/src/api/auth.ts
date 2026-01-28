import http from './http';

export const login = (data: { username: string; password: string }) => http.post('/auth/login', data);
export const register = (data: { username: string; password: string; roleCode?: string }) =>
  http.post('/auth/register', data);
export const getMe = () => http.get('/auth/me');

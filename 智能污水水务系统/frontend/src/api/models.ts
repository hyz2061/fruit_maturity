import http from './http';

export const getModels = () => http.get('/models');
export const deployModel = (id: number | string) => http.post('/models/deploy', { id });

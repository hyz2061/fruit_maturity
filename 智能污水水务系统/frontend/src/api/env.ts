import http from './http';

export const getEnvMetrics = (params?: any) => http.get('/env/metrics', { params });
export const createEnvMetric = (data: any) => http.post('/env/metrics', data);
export const deleteEnvMetric = (id: number | string) => http.delete(`/env/metrics/${id}`);

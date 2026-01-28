import http from './http';

export const getReports = () => http.get('/reports');
export const exportReport = () => http.post('/reports/export');
export const exportExcel = () => http.get('/reports/export/excel', { responseType: 'blob' });
export const exportPdf = () => http.get('/reports/export/pdf', { responseType: 'blob' });

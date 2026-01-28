import http from './http';

export const getUsers = () => http.get('/system/users');
export const getRoles = () => http.get('/system/roles');
export const getDicts = () => http.get('/system/dicts');
export const getAudits = () => http.get('/system/audits');

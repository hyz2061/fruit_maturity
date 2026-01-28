import http from './http';

export const getVisionStat = () => http.get('/vision/stat');
export const getVisionDataset = () => http.get('/vision/dataset');

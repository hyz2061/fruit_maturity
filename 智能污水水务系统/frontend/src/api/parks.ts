import http from './http';

export const getParks = () => http.get('/parks');
export const getGreenhouses = (parkId?: number | string) => http.get('/greenhouses', { params: { parkId } });

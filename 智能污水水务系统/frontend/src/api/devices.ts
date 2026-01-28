import http from './http';

export const getDevices = (params?: any) => http.get('/devices', { params });
export const bindDevice = (id: number | string, greenhouseId: number) =>
  http.patch(`/devices/${id}/bind`, { greenhouseId });
export const updateDeviceStream = (id: number | string, streamUrl: string) =>
  http.patch(`/devices/${id}/stream`, { streamUrl });

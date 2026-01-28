import http from './http';

export const getEvents = (params?: any) => http.get('/events', { params });
export const getEventsPage = (params?: any) => http.get('/events/page', { params });
export const getEventDetail = (id: number | string) => http.get(`/events/${id}`);
export const createEvent = (data: any) => http.post('/events', data);
export const updateEventStatus = (id: number | string, status: string) =>
  http.patch(`/events/${id}/status`, { status });
export const updateEventReview = (id: number | string, data: any) =>
  http.patch(`/events/${id}/review`, data);
export const deleteEvent = (id: number | string) => http.delete(`/events/${id}`);
export const getEventsRecent = (params?: any) => http.get('/events/recent', { params });

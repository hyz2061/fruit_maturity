import http from './http';

export const chatAi = (message: string, sessionId?: string) =>
  http.post('/ai/chat', { message, sessionId });

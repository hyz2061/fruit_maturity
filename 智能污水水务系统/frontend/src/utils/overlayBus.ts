export type OverlayBox = {
  x: number;
  y: number;
  w: number;
  h: number;
  label?: string;
  score?: number;
  color?: string;
};

export type OverlayPayload = {
  cameraId: string | number;
  boxes: OverlayBox[];
  counts?: Record<string, number>;
  ts?: number;
};

type Handler = (payload: OverlayPayload) => void;

const listeners = new Map<string, Set<Handler>>();

const getKey = (cameraId: string | number) => String(cameraId);

export const overlayBus = {
  subscribe(cameraId: string | number, handler: Handler) {
    const key = getKey(cameraId);
    if (!listeners.has(key)) listeners.set(key, new Set());
    listeners.get(key)!.add(handler);
    return () => {
      listeners.get(key)?.delete(handler);
    };
  },
  publish(cameraId: string | number, payload: OverlayPayload) {
    const key = getKey(cameraId);
    const set = listeners.get(key);
    if (!set || set.size === 0) return;
    set.forEach(fn => fn(payload));
  },
};

export class WebSocketClient {
  private ws?: WebSocket;
  connect(url: string) {
    this.ws = new WebSocket(url);
  }
  send(data: any) {
    this.ws?.send(JSON.stringify(data));
  }
  close() {
    this.ws?.close();
  }
}

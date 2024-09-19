import { Notification } from '../types'

type WebSocketCallback = (notification: Notification) => void

export const initializeWebSocket = (callback: WebSocketCallback): (() => void) => {
  
  const socket = new WebSocket(`${process.env.NEXT_PUBLIC_PAYMENT_WEBSOCKET_NOTIFICATION}/transaction/notifications`);
  
  socket.onmessage = (event) => {
    const data = JSON.parse(event.data)
    const notificationType = data.message.includes('HighAmount') ? 'HighAmount' : 'Failure'
    const notification: Notification = {
      type: notificationType,
      Transaction: data.Transaction
    }
    callback(notification)
  }

  return () => socket.close()
}
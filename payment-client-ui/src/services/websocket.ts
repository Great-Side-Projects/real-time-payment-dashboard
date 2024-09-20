import { Client, messageCallbackType } from '@stomp/stompjs';
import { Notification } from '../types';

type WebSocketCallback = (notification: Notification) => void;

export const initializeWebSocket = (callback: WebSocketCallback): (() => void) => {
  const client = new Client({
    brokerURL: `${process.env.NEXT_PUBLIC_PAYMENT_WEBSOCKET_NOTIFICATION}`,
    debug: function (/*str*/) {
    //  console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.onConnect = function (/*frame*/) {
    //console.log('Connected: ' + frame);
    client.subscribe('/transaction/notifications', function (message) {
      const data = JSON.parse(message.body);
      //console.log('Received json: ' + data);
      
      const notificationType = data.notificationtype;
      const notification: Notification = {
        type: notificationType,
        Transaction: data.transaction
      };
      //console.log('Received notification: ' + notification);
      callback(notification);
    } as messageCallbackType);
  };

  client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate();

  return () => {
    if (client.active) {
      client.deactivate();
    }
  };
};
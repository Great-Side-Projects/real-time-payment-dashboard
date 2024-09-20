package org.dev.client;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;


import java.lang.reflect.Type;

public class MyStompSessionHandler implements StompSessionHandler {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe("/transaction/notifications", this);
        System.out.println("New session established : " + session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Got an exception " + exception.getMessage());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.out.println("Got an error " + exception.getMessage());
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return  byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        String msg = new String((byte[]) payload);
        System.out.println(msg);
    }
}

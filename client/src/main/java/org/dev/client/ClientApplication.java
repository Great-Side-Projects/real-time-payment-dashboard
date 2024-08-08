package org.dev.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(ClientApplication.class, args);

        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        //stompClient.setMessageConverter(new SimpleMessageConverter());

        StompSessionHandler sessionHandler = new MyStompSessionHandler();

        stompClient.connect(uri.toString(), sessionHandler);
        System.out.println("ClientApplication started successfully");
        new Scanner(System.in).nextLine(); // Don't close immediately.
    }
}

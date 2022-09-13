package com.lxisoft.hungryzone.web.websocket;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

class ActivityServiceTest {

    Logger logger = LoggerFactory.getLogger(ActivityServiceTest.class);
    static final String ACCESS_TOKEN =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY2NTU4MTE3MX0.7frc4x0IhAzkLKdX_ubuow_bjW_CbhA1H7q5c00u-bItdTyYFX63_nanRUdx4vc2ncNLACpwvXlCBpXSj2lu0A";
    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket/tracker";
    static final String WEBSOCKET_TOPIC = "/topic";

    BlockingQueue<String> blockingQueue;
    WebSocketStompClient stompClient;

    @BeforeEach
    void setUp() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void shouldReceiveAMessageFromTheServer() throws Exception {
        StompSession session = stompClient
            .connect(WEBSOCKET_URI + "?access_token=" + ACCESS_TOKEN, new StompSessionHandlerAdapter() {})
            .get(4, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC + "/test", new DefaultStompFrameHandler());
        Thread.sleep(10000L);
        /*String message = "MESSAGE TEST";
        session.send(WEBSOCKET_TOPIC, message.getBytes());*/

        //Assert.assertEquals(message, blockingQueue.poll(1, SECONDS));
        logger.info("blocking queue: {}", blockingQueue);
    }

    class DefaultStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            logger.info("message received: {}", o);
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}

package com.lxisoft.hungryzone.mytest;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.lxisoft.hungryzone.web.rest.WebSocketTest;
import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@DataJpaTest
public class ChatTest {

    Logger logger = LoggerFactory.getLogger(ChatTest.class);
    static final String ACCESS_TOKEN =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTY2ODc1Mzg5M30.HquSC93sdUoT_dAchzMmEe-g3_tVFcryaQ48-vTcrxdJVzYIFhUKXFZB5eV9cxv5O5j1EsgfwrPT9V452Mcyfw";

    static final String WEBSOCKET_URI = "ws://localhost:8080/websocket/tracker";

    static final String WEBSOCKET_TOPIC = "/topic";

    BlockingQueue<String> blockingQueue;

    WebSocketStompClient stompClient;

    @Before
    public void setup() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(new SockJsClient(asList(new WebSocketTransport(new StandardWebSocketClient()))));
    }

    @Test
    public void save() throws Exception {
        StompSession session = stompClient
            .connect(WEBSOCKET_URI + "?access_token=" + ACCESS_TOKEN, new StompSessionHandlerAdapter() {})
            .get(20, SECONDS);

        session.subscribe(WEBSOCKET_TOPIC + "/chat", new ChatTest.DefaultStompFrameHandler());

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

package com.lxisoft.hungryzone.web.websocket;

import com.lxisoft.hungryzone.web.websocket.dto.ChatMessageDTO;
import com.lxisoft.hungryzone.web.websocket.util.StringUtils;
import java.security.Principal;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ChatService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(ActivityService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/broadcast")
    @SendTo("/chat/messages")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage, StompHeaderAccessor stompHeaderAccessor, Principal principal)
        throws Exception {
        chatMessage.setUserLogin(principal.getName());
        chatMessage.setTime(Instant.now());
        return chatMessage;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {}
}

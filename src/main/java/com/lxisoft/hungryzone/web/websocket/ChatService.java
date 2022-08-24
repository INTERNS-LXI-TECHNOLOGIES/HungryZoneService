package com.lxisoft.hungryzone.web.websocket;

import com.lxisoft.hungryzone.web.websocket.dto.ChatMessageDTO;
import com.lxisoft.hungryzone.web.websocket.util.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatService {

    @MessageMapping("/broadcast")
    @SendTo("/chat/messages")
    public ChatMessageDTO send(ChatMessageDTO chatMessage) throws Exception {
        return new ChatMessageDTO(
            chatMessage.getFrom(),
            chatMessage.getText(),
            chatMessage.getRecipient(),
            StringUtils.getCurrentTimeStamp()
        );
    }
}

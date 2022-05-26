package pl.rokolujka.springreactludo.webSocketsConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public WebSocketChatMessage receivePublicMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
        return webSocketChatMessage;
    }

    @MessageMapping("/private-message")
    public WebSocketChatMessage receivePrivateMessage(@Payload WebSocketChatMessage webSocketChatMessage) {
        simpMessagingTemplate.convertAndSendToUser(webSocketChatMessage.getReceiver(),"/private",webSocketChatMessage);
        return webSocketChatMessage;
    }

}
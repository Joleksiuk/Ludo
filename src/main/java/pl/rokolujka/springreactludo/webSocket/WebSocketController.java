package pl.rokolujka.springreactludo.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;



@RequiredArgsConstructor
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/invite/friend")
    public void sendMessage(@Payload PlayerFriendInvite invite) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/invite.friend.%d", invite.getInvitedPlayerId()), invite);
    }
}
package pl.rokolujka.springreactludo.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

import java.util.Random;


@RequiredArgsConstructor
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/invite/friend")
    public void sendMessage(@Payload PlayerFriendInvite invite) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/invite.friend.%d", invite.getInvitedPlayerId()), invite);
    }

    @MessageMapping("/game/{gameId}/dice")
    @SendTo("/topic/game.{gameId}")
    public GameStatusMessage sendStatusMessage(@DestinationVariable Integer gameId) {
        return GameStatusMessage.builder()
                .diceValue(getDiceValue())
                .build();
    }

    private Integer getDiceValue() {
        return new Random().nextInt(6);
    }
}
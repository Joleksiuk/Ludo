package pl.rokolujka.springreactludo.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerService;
import pl.rokolujka.springreactludo.lobby.LobbyModel;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GamePlayerService gamePlayerService;
    private final GameService gameService;

    @MessageMapping("/invite/friend")
    public void sendMessage(@Payload PlayerFriendInvite invite) {
        simpMessagingTemplate.convertAndSend(String.format("/topic/invite.friend.%d", invite.getInvitedPlayerId()), invite);
    }

    @MessageMapping("/lobby/{gameId}/game-start")
    @SendTo("/topic/game-start.lobby.{gameId}")
    public LobbyStatusMessage sendGameStartedMessage(
            @DestinationVariable Integer gameId,
            List<LobbyModel> lobbyModelList) {

        gameService.startGame(gameService.findGameById(gameId),lobbyModelList);
        gameService.setStartGameTimestamp(gameId);
        return LobbyStatusMessage.builder()
                .gameStarted(true)
                .build();
    }

    @MessageMapping("/lobby/{gameId}/color-picked")
    @SendTo("/topic/color-pick.lobby.{gameId}")
    public LobbyStatusMessage sendColorPickedMessage(@DestinationVariable Integer gameId,
                                                     GamePlayer gamePlayer) {
        gamePlayerService.updateGamePlayer(gamePlayer);
        return LobbyStatusMessage.builder()
                .playerId(gamePlayer.getPlayerId())
                .playerColour(gamePlayer.getPlayerColour())
                .build();
    }
}
package pl.rokolujka.springreactludo.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.rokolujka.springreactludo.authentication.JwtUtils;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerService;
import pl.rokolujka.springreactludo.player.PlayerService;
import pl.rokolujka.springreactludo.lobby.LobbyModel;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInviteService;
import pl.rokolujka.springreactludo.playerGameInvite.PlayerGameInvite;
import pl.rokolujka.springreactludo.playerGameInvite.PlayerGameInviteService;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GamePlayerService gamePlayerService;
    private final JwtUtils jwtUtils;
    private final PlayerGameInviteService playerGameInviteService;
    private final PlayerService playerService;
    private final PlayerFriendInviteService playerFriendInviteService;
    private final GameService gameService;

    @MessageMapping("/invite/friend")
    public void sendFriendInvite(@Payload PlayerFriendInvite invite) {
        playerFriendInviteService.createPlayerFriendInvite(invite);
        simpMessagingTemplate.convertAndSend(String.format("/topic/invite.friend.%d", invite.getInvitedPlayerId()),
                playerService.findPlayerById(invite.getInvitingPlayerId()));
    }

    @MessageMapping("/invite/game")
    public void sendGameInvite(@Payload PlayerGameInvite invite) {
        playerGameInviteService.createPlayerGameInvite(invite);
        simpMessagingTemplate.convertAndSend(
                String.format("/topic/invite.game.%d", invite.getInvitedPlayerId()),
                playerGameInviteService.inviteToGameAndPlayer(invite));
    }

    @MessageMapping("/invite/game/accept")
    public void acceptGameInvite(@Payload PlayerGameInvite invite, @Header(name="Authorization") String authHeader) {
        String nickname = jwtUtils.getPlayerNameFromAuthorizationHeader(authHeader);
        gamePlayerService.createFromGameInvite(invite);
        simpMessagingTemplate.convertAndSend(String.format("/topic/game.join.%d", invite.getGameId()), playerService.findPlayerByNickname(nickname));
        playerGameInviteService.deletePlayerGameInvite(invite);
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
package pl.rokolujka.springreactludo.webSocket;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import pl.rokolujka.springreactludo.authentication.JwtUtils;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.game.board.BoardMove;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;
import pl.rokolujka.springreactludo.player.Player;

import java.util.List;

@RequiredArgsConstructor
@MessageMapping("/game/{gameId}")
@Controller
public class GameWebSocketController {

    private static final String GAME_TOPIC_PREFIX = "/topic/game-";
    private static final String MOVE_TOPIC = GAME_TOPIC_PREFIX + "move.{gameId}";

    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JwtUtils jwtUtils;

    @MessageMapping("dice")
    public void handleDiceRoll(@DestinationVariable Integer gameId, @Header(name="Authorization") String authHeader) {
        String nickname = jwtUtils.getPlayerNameFromAuthorizationHeader(authHeader);
        if (gameService.isOtherPlayersTurn(nickname, gameId)) {
            return;
        }

        Integer diceValue = gameService.rollDice(gameId);
        List<BoardMove> possibleMoves = gameService.getPossibleMoves(nickname, gameId);
        if (possibleMoves.isEmpty()) {
            gameService.nextTurn(gameId);
            sendTurn(gameId, gameService.getGameTurn(gameId));
        }
        sendPossibleMoves(gameId, nickname, possibleMoves);
        sendDiceValue(gameId, diceValue);
    }

    @MessageMapping("move")
    @SendTo(MOVE_TOPIC)
    public List<BoardMove> handleMove(@DestinationVariable Integer gameId,
                                @Payload PawnInfo pawnInfo,
                                @Header(name="Authorization") String authHeader) {
        String nickname = jwtUtils.getPlayerNameFromAuthorizationHeader(authHeader);
        if (gameService.isOtherPlayersTurn(nickname, gameId)) {
            return List.of();
        }
        List<BoardMove> moves = gameService.makeMoveWithPawn(pawnInfo, nickname, gameId);
        gameService.nextTurn(gameId);
        sendTurn(gameId, gameService.getGameTurn(gameId));
        gameService.findWinner(gameId).ifPresent(winner -> sendWinner(gameId, winner));
        return moves;
    }

    private void sendDiceValue(Integer gameId, Integer diceValue) {
        simpMessagingTemplate.convertAndSend(String.format("%s%s.%d", GAME_TOPIC_PREFIX, "dice", gameId), diceValue);
    }

    private void sendPossibleMoves(Integer gameId, String nickname, List<BoardMove> possibleMoves) {
        simpMessagingTemplate.convertAndSend(String.format("%s%s.%d.%s", GAME_TOPIC_PREFIX,"possible-move", gameId, nickname), possibleMoves);
    }

    private void sendWinner(Integer gameId, Player winnerPlayer) {
        simpMessagingTemplate.convertAndSend(String.format("%s%s.%d", GAME_TOPIC_PREFIX, "winner", gameId), winnerPlayer);
    }

    private void sendTurn(Integer gameId, Player currentPlayer) {
        simpMessagingTemplate.convertAndSend(String.format("%s%s.%d",GAME_TOPIC_PREFIX, "turn", gameId), currentPlayer);
    }
}

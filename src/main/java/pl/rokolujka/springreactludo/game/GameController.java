package pl.rokolujka.springreactludo.game;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.authentication.JwtUtils;
import pl.rokolujka.springreactludo.game.board.BoardField;
import pl.rokolujka.springreactludo.game.board.BoardMove;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;
import pl.rokolujka.springreactludo.player.Player;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("games")
@RestController
public class GameController {

    private final GameService gameService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.findAllGames();
    }

    @PostMapping
    public void createGame(@RequestBody Game game, @RequestHeader(name = "Authorization") String headerAuth) {
        String nick = jwtUtils.getPlayerNameFromAuthorizationHeader(headerAuth);
        gameService.createGame(game, nick);
    }

    @PutMapping
    public void updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
    }

    @DeleteMapping("{id}")
    public void deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
    }

    @GetMapping("{id}/pawns")
    public List<PawnInfo> getGamePawns(@PathVariable Integer id) {
        return gameService.findGamePawnsById(id);
    }

    @GetMapping("{id}/dice")
    public Integer getDiceValue(@PathVariable Integer id) {
        return gameService.getDiceValue(id);
    }

    @GetMapping("{id}/possible-moves")
    public List<BoardMove> getPossibleMoves(@PathVariable Integer id, @RequestHeader(name="Authorization") String authHeader) {
        String nickname = jwtUtils.getPlayerNameFromAuthorizationHeader(authHeader);
        if (gameService.isOtherPlayersTurn(nickname, id)) {
            return List.of();
        }
        return gameService.getPossibleMoves(nickname, id);
    }

    @GetMapping("player/{playerId}")
    public List<Game> getGamesByPlayerId(@PathVariable Integer playerId) {
        return gameService.findByPlayerId(playerId);
    }

    @GetMapping(value="{id}")
    public Game getGameById(@PathVariable Integer id){
        return gameService.findGameById(id);
    }

    @GetMapping(value = "{id}/board/fields")
    public List<List<BoardField>> getGameBoardFields(@PathVariable Integer id) {
       return gameService.getGameFieldMatrixById(id);
    }

    @GetMapping("{id}/winner")
    public Player getGameWinner(@PathVariable Integer id) {
        return gameService.findWinner(id).orElse(null);
    }

    @GetMapping("{id}/turn")
    public Player getGameTurnPlayer(@PathVariable Integer id) {
        return gameService.getGameTurn(id);

    }
    @PutMapping(value="{id}/start")
    public void startGame(@PathVariable Integer id){
        gameService.setStartGameTimestamp(id);
    }
}

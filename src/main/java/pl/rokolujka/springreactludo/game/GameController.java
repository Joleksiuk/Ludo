package pl.rokolujka.springreactludo.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.game.board.BoardField;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;

import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping("games")
    public List<Game> getAllGames() {
        return gameService.findAllGames();
    }

    @RequestMapping(method= RequestMethod.POST, value="game")
    public void createGame(@RequestBody Game game) {gameService.createGame(game);}

    @RequestMapping(method=RequestMethod.PUT, value="game")
    public void updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="game/{id}")
    public void deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
    }

    @RequestMapping(method=RequestMethod.GET, value="game/{id}/pawns")
    public List<PawnInfo> getGamePawns(@PathVariable Integer id) {
        return gameService.findGamePawnsById(id);
    }

    @RequestMapping(value = "game/{id}/board/fields")
    public List<List<BoardField>> getGameBoardFields(@PathVariable Integer id) {
       return gameService.getGameFieldMatrixById(id);
    }
}

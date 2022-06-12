package pl.rokolujka.springreactludo.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.game.board.BoardField;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.findAllGames();
    }

    @PostMapping
    public void createGame(@RequestBody Game game) {gameService.createGame(game);}

    @PutMapping
    public void updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
    }

    @DeleteMapping(value="{id}")
    public void deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
    }

    @GetMapping(value="{id}/pawns")
    public List<PawnInfo> getGamePawns(@PathVariable Integer id) {
        return gameService.findGamePawnsById(id);
    }

    @GetMapping(value="{id}")
    public Game getGameById(@PathVariable Integer id){
        return gameService.findGameById(id);
    }

    @GetMapping(value = "{id}/board/fields")
    public List<List<BoardField>> getGameBoardFields(@PathVariable Integer id) {
       return gameService.getGameFieldMatrixById(id);
    }
    @PutMapping(value="{id}/start")
    public void startGame(@PathVariable Integer id){
        gameService.setStartGameTimestamp(id);
    }
}

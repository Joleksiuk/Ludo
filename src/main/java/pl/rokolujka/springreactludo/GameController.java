package pl.rokolujka.springreactludo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void createGame(@RequestBody Game game) {
        gameService.createGame(game);

    }

    @RequestMapping(method=RequestMethod.PUT, value="game")
    public void updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="game/{id}")
    public void deleteGame(@PathVariable Integer id) {
        gameService.deleteGameById(id);
    }
}

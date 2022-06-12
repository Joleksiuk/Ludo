package pl.rokolujka.springreactludo.game.gamePlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("game_players")
public class GamePlayerController {

    private final GamePlayerService gamePlayerService;

    @Autowired
    public GamePlayerController(GamePlayerService gamePlayerService) {
        this.gamePlayerService = gamePlayerService;
    }

    @GetMapping
    public List<GamePlayer> getAllPlayersGame(){
        return this.gamePlayerService.findAllGamePlayers();
    }

    @PostMapping
    public void createPlayerGameInvite(@RequestBody GamePlayer gamePlayer) {
        gamePlayerService.createPlayerGameInvite(gamePlayer);
    }

    @DeleteMapping
    public void deletePlayerFriend(@RequestBody GamePlayer gamePlayer) {
        gamePlayerService.deletePlayerGameInvite(gamePlayer);
    }

    @PutMapping
    public void updatePlayerGame(@RequestBody GamePlayer gamePlayer){

        gamePlayerService.updateGamePlayer(gamePlayer);
    }

    @GetMapping("/{gameId}")
    public List<GamePlayer> getAllPlayersOfGameByGameId(@PathVariable Integer gameId){
        return gamePlayerService.findAllGamePlayersByGameId(gameId);
    }

}

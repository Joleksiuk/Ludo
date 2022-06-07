package pl.rokolujka.springreactludo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("lobby")
public class LobbyController {

    private final  PlayerService playerService;

    @Autowired
    public LobbyController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("invited/{id}")
    public List<Player> findAllPlayersInvitedToGameByGameId(@PathVariable Integer id) {
        return playerService.findAllPlayersInvitedToGameByGameId(id);
    }

    @GetMapping("{id}")
    public List<Player> findAllPlayersOfGameByGameId(@PathVariable Integer id) {
        return playerService.findAllPlayersOfGameByGameId(id);
    }
}

package pl.rokolujka.springreactludo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping("players")
    public List<Player> getAllPlayers() {
        return playerService.findAllPlayers();
    }

    @RequestMapping(method= RequestMethod.POST, value="player")
    public void createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player);
    }

    @RequestMapping(method=RequestMethod.PUT, value="player")
    public void updatePlayer(@RequestBody Player player) {
        playerService.updatePlayer(player);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="player/{id}")
    public void deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayerById(id);
    }
}

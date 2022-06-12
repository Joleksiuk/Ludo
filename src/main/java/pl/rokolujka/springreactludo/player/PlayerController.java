package pl.rokolujka.springreactludo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.findAllPlayers();
    }

    @PostMapping
    public void createPlayer(@RequestBody Player player) {
        playerService.createPlayer(player);
    }

    @PutMapping
    public void updatePlayer(@RequestBody Player player) {
        playerService.updatePlayer(player);
    }

    @PutMapping("gravatar")
    public void updateGravatar(@RequestBody Player player) {
        playerService.updateGravatar(player);
    }

    @DeleteMapping("{id}")
    public void deletePlayer(@PathVariable Integer id) {
        playerService.deletePlayerById(id);
    }

    @GetMapping("{id}")
    public Player findPlayerById(@PathVariable Integer id) {return playerService.findPlayerById(id).orElse(null);}

    @GetMapping("friends/{id}")
    public List<Player> findAllPlayerFriends(@PathVariable Integer id) {
        return playerService.findAllFriendsOfPlayer(id);
    }
    @GetMapping("suggest_friends/{id}")
    public List<Player> findAllSuggestedPlayerFriends(@PathVariable Integer id) {
        return playerService.findAllSuggestedPlayerFriends(id);
    }




}

package pl.rokolujka.springreactludo.playerFriend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.player.Player;

import java.util.List;

@RestController
@RequestMapping("player_friends")
public class PlayerFriendController {

    private final PlayerFriendService playerFriendService;

    @Autowired
    public PlayerFriendController(PlayerFriendService playerFriendService) {
        this.playerFriendService = playerFriendService;
    }

    @GetMapping
    public List<PlayerFriend> getAllFriendships() {
        return playerFriendService.findAllPlayerFriends();
    }

    @PostMapping
    public void createPlayerFriend(@RequestBody PlayerFriend playerFriend) {
        playerFriendService.createPlayerFriend(playerFriend);
    }

    @DeleteMapping
    public void deletePlayerFriend(@RequestBody PlayerFriend playerFriend) {
        playerFriendService.deletePlayerFriend(playerFriend);
    }

}

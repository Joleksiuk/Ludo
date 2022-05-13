package pl.rokolujka.springreactludo.playerFriend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerFriendController {

    private final PlayerFriendService playerFriendService;

    @Autowired
    public PlayerFriendController(PlayerFriendService playerFriendService) {
        this.playerFriendService = playerFriendService;
    }

    @RequestMapping("player_friends")
    public List<PlayerFriend> getAllFriendships() {
        return playerFriendService.findAllPlayerFriends();
    }

    @RequestMapping(method= RequestMethod.POST, value="player_friend")
    public void createPlayerFriend(@RequestBody PlayerFriend playerFriend) {
        playerFriendService.createPlayerFriend(playerFriend);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="player_friend")
    public void deletePlayerFriend(@RequestBody PlayerFriend playerFriend) {
        playerFriendService.deletePlayerFriend(playerFriend);
    }

}

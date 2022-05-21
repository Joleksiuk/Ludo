package pl.rokolujka.springreactludo.playerFriendInvite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriend;

import java.util.List;

@RestController
public class PlayerFriendInviteController {

    private final PlayerFriendInviteService playerFriendInviteService;

    @Autowired
    public PlayerFriendInviteController(PlayerFriendInviteService playerFriendInviteService) {
        this.playerFriendInviteService = playerFriendInviteService;
    }

    @RequestMapping("player_friend_invites")
    public List<PlayerFriendInvite> getAllPlayerFriendInvites() {
        return playerFriendInviteService.findAllPlayerFriendInvites();
    }

    @RequestMapping(method= RequestMethod.POST, value="player_friend_invite")
    public void createPlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.createPlayerFriendInvite(playerFriendInvite);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="player_friend_invite")
    public void deletePlayerFriend(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.deletePlayerFriendInvite(playerFriendInvite);
    }

    @RequestMapping(method=RequestMethod.POST, value = "player_friend_request")
    public void sendPlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite ){
        playerFriendInviteService.sendFriendRequest(playerFriendInvite);
    }
}

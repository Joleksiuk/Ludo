package pl.rokolujka.springreactludo.playerGameInvite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

import java.util.List;

@RestController
public class PlayerGameInviteController {

    private final PlayerGameInviteService playerGameInviteService;

    @Autowired
    public PlayerGameInviteController(PlayerGameInviteService playerGameInviteService) {
        this.playerGameInviteService = playerGameInviteService;
    }

    @RequestMapping("player_game_invites")
    public List<PlayerGameInvite> getAllPlayerGameInvites() {
        return playerGameInviteService.findAllPlayerGameInvites();
    }

    @RequestMapping(method= RequestMethod.POST, value="player_game_invite")
    public void createPlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.createPlayerGameInvite(playerGameInvite);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="player_game_invite")
    public void deletePlayerFriend(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.deletePlayerGameInvite(playerGameInvite);
    }

    @RequestMapping(method=RequestMethod.POST, value = "send_game_invite")
    public void sendPlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite ){
        playerGameInviteService.sendGameInvite(playerGameInvite);
    }

}
package pl.rokolujka.springreactludo.playerGameInvite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

import java.util.List;

@RestController
@RequestMapping("player_game_invites")
public class PlayerGameInviteController {

    private final PlayerGameInviteService playerGameInviteService;

    @Autowired
    public PlayerGameInviteController(PlayerGameInviteService playerGameInviteService) {
        this.playerGameInviteService = playerGameInviteService;
    }

    @GetMapping
    public List<PlayerGameInvite> getAllPlayerGameInvites() {
        return playerGameInviteService.findAllPlayerGameInvites();
    }

    @PostMapping
    public void createPlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.createPlayerGameInvite(playerGameInvite);
    }

    @DeleteMapping
    public void deletePlayerFriend(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.deletePlayerGameInvite(playerGameInvite);
    }

    @PostMapping("send")
    public void sendPlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite ){
        playerGameInviteService.sendGameInvite(playerGameInvite);
    }

}
package pl.rokolujka.springreactludo.playerFriendInvite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("player_friend_invites")
public class PlayerFriendInviteController {

    private final PlayerFriendInviteService playerFriendInviteService;

    @Autowired
    public PlayerFriendInviteController(PlayerFriendInviteService playerFriendInviteService) {
        this.playerFriendInviteService = playerFriendInviteService;
    }

    @GetMapping
    public List<PlayerFriendInvite> getAllPlayerFriendInvites() {
        return playerFriendInviteService.findAllPlayerFriendInvites();
    }

    @PostMapping
    public void createPlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.createPlayerFriendInvite(playerFriendInvite);
    }

    @DeleteMapping
    public void deletePlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.deletePlayerFriendInvite(playerFriendInvite);
    }

    @PutMapping("accept")
    public void acceptPlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.acceptFriendInvite(playerFriendInvite);
    }

    @PutMapping("decline")
    public void declinePlayerFriendInvite(@RequestBody PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteService.declineFriendInvite(playerFriendInvite);
    }






}

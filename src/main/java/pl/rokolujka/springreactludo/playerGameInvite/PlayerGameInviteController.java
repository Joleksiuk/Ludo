package pl.rokolujka.springreactludo.playerGameInvite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("player-game-invites")
public class PlayerGameInviteController {

    private final PlayerGameInviteService playerGameInviteService;

    @Autowired
    public PlayerGameInviteController(PlayerGameInviteService playerGameInviteService) {
        this.playerGameInviteService = playerGameInviteService;
    }

    @GetMapping("{invitedPlayerId}")
    public List<GameAndPlayer> getPlayersGameInvites(@PathVariable Integer invitedPlayerId) {
        return playerGameInviteService.findPlayerGameInvitesByInvitedPlayerId(invitedPlayerId);
    }

    @PostMapping
    public void createPlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.createPlayerGameInvite(playerGameInvite);
    }

    @PutMapping("decline")
    public void declinePlayerGameInvite(@RequestBody PlayerGameInvite playerGameInvite) {
        playerGameInviteService.deletePlayerGameInvite(playerGameInvite);
    }
}
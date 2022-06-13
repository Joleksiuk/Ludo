package pl.rokolujka.springreactludo.lobby;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerService;

import java.util.List;

@RestController
@RequestMapping("lobby")
public class LobbyController {

    private final PlayerService playerService;
    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(PlayerService playerService, LobbyService lobbyService) {
        this.playerService = playerService;
        this.lobbyService = lobbyService;
    }

    @GetMapping("invited/{id}")
    public List<Player> findAllPlayersInvitedToGameByGameId(@PathVariable Integer id) {
        return playerService.findAllPlayersInvitedToGameByGameId(id);
    }

    @GetMapping("{id}/players")
    public List<Player> findAllPlayersOfGameByGameId(@PathVariable Integer id) {
        return playerService.findAllPlayersOfGameByGameId(id);
    }
    @GetMapping("{gameId}")
    public Lobby findLobbyByGameId(@PathVariable Integer gameId){
        return lobbyService.findLobbyByGameId(gameId);
    }

    @GetMapping("models/{gameId}")
    public List<LobbyModel> findAllLobbyParticipantsByGameId(@PathVariable Integer gameId){
        return lobbyService.getLobbyParticipantsByGameId(gameId);
    }

    @GetMapping("colors/{gameId}")
    public List<String> findAllAvailableBoardColors(@PathVariable Integer gameId){
        return lobbyService.getAvailableBoardColorsByGameId(gameId);
    }
}

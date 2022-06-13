package pl.rokolujka.springreactludo.playerGameInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.player.PlayerService;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlayerGameInviteService {

    private final PlayerGameInviteRepository playerGameInviteRepository;
    private final PlayerService playerService;
    private final GameService gameService;

    public List<PlayerGameInvite> findAllPlayerGameInvites() {
        List<PlayerGameInvite> playerGameInvites = new LinkedList<>();
        playerGameInviteRepository.findAll().forEach(playerGameInvites::add);
        return playerGameInvites;
    }

    public List<GameAndPlayer> findPlayerGameInvitesByInvitedPlayerId(Integer invitedId) {
        return playerGameInviteRepository.findByInvitedPlayerId(invitedId)
                .stream()
                .map(this::inviteToGameAndPlayer)
                .collect(Collectors.toList());
    }

    public GameAndPlayer inviteToGameAndPlayer(PlayerGameInvite invite) {
        return new GameAndPlayer(
                gameService.findById(invite.getGameId()).orElseThrow(),
                playerService.findPlayerById(invite.getInvitingPlayerId()).orElseThrow());
    }

    public void createPlayerGameInvite(PlayerGameInvite playerGameInvite) {
        playerGameInviteRepository.save(playerGameInvite);
    }

    public void deletePlayerGameInvite(PlayerGameInvite playerGameInvite) {
        playerGameInviteRepository.delete(playerGameInvite);
    }
}

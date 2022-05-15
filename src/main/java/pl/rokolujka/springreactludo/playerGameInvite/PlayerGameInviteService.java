package pl.rokolujka.springreactludo.playerGameInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerGameInviteService {

    private final PlayerGameInviteRepository playerGameInviteRepository;

    public List<PlayerGameInvite> findAllPlayerGameInvites() {
        List<PlayerGameInvite> playerGameInvites = new LinkedList<>();
        playerGameInviteRepository.findAll().forEach(playerGameInvites::add);
        return playerGameInvites;
    }

    public void createPlayerGameInvite(PlayerGameInvite playerGameInvite) {
        playerGameInviteRepository.save(playerGameInvite);
    }

    public void deletePlayerGameInvite(PlayerGameInvite playerGameInvite) {
        playerGameInviteRepository.delete(playerGameInvite);
    }

}

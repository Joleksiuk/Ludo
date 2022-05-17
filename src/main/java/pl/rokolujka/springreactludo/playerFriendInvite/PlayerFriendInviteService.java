package pl.rokolujka.springreactludo.playerFriendInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerFriendInviteService {

    private final PlayerFriendInviteRepository playerFriendInviteRepository;

    public List<PlayerFriendInvite> findAllPlayerFriendInvites() {
        List<PlayerFriendInvite> playerFriendInvites = new LinkedList<>();
        playerFriendInviteRepository.findAll().forEach(playerFriendInvites::add);
        return playerFriendInvites;
    }

    public void createPlayerFriendInvite(PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteRepository.save(playerFriendInvite);
    }
    public void deletePlayerFriendInvite(PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteRepository.delete(playerFriendInvite);
    }

}

package pl.rokolujka.springreactludo.playerFriendInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerService;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriend;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriendRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlayerFriendInviteService {

    private final PlayerFriendInviteRepository playerFriendInviteRepository;
    private final PlayerFriendRepository playerFriendRepository;
    private final PlayerService playerService;

    public List<Player> findPlayersInvitingPlayer(Integer invitedPlayerId) {
        return playerFriendInviteRepository.findByInvitedPlayerId(invitedPlayerId)
                .stream()
                .map(invite -> playerService.findPlayerById(invite.getInvitingPlayerId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public void createPlayerFriendInvite(PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteRepository.save(playerFriendInvite);
    }
    public void deletePlayerFriendInvite(PlayerFriendInvite playerFriendInvite) {
        playerFriendInviteRepository.delete(playerFriendInvite);
    }

    public void acceptFriendInvite(PlayerFriendInvite playerFriendInvite){
        PlayerFriend playerFriend=new PlayerFriend(playerFriendInvite.invitedPlayerId,playerFriendInvite.invitingPlayerId);
        playerFriendRepository.save(playerFriend);
        deletePlayerFriendInvite(playerFriendInvite);
    }

    public void declineFriendInvite(PlayerFriendInvite playerFriendInvite){
        deletePlayerFriendInvite(playerFriendInvite);
    }

}

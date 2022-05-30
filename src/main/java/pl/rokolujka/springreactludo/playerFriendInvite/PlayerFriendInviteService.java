package pl.rokolujka.springreactludo.playerFriendInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriend;
import pl.rokolujka.springreactludo.playerFriend.PlayerFriendRepository;
import pl.rokolujka.springreactludo.rabbitMQ.RabbitConfig;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerFriendInviteService {

    private final RabbitTemplate rabbitTemplate;

    private final PlayerFriendInviteRepository playerFriendInviteRepository;
    private final PlayerFriendRepository playerFriendRepository;

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

    public void acceptFriendInvite(PlayerFriendInvite playerFriendInvite){
        PlayerFriend playerFriend=new PlayerFriend(playerFriendInvite.invitedUserId,playerFriendInvite.invitingUserId);
        playerFriendRepository.save(playerFriend);
        deletePlayerFriendInvite(playerFriendInvite);
    }

    public void declineFriendInvite(PlayerFriendInvite playerFriendInvite){
        deletePlayerFriendInvite(playerFriendInvite);
    }

}

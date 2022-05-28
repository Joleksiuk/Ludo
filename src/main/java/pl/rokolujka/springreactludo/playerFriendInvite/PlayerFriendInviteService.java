package pl.rokolujka.springreactludo.playerFriendInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.rabbitMQ.FriendInviteDto;
import pl.rokolujka.springreactludo.rabbitMQ.RabbitConfig;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerFriendInviteService {

    private final RabbitTemplate rabbitTemplate;

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

    public void sendFriendInvite(PlayerFriendInvite playerFriendInvite){

        createPlayerFriendInvite(playerFriendInvite);

        //send friend invite notification to rabbitMQ queue
        String message = "Player ["+playerFriendInvite.getInvitingUserId()+"] sent friend request to player ["+ playerFriendInvite.getInvitedUserId() +"]";
        FriendInviteDto friendInviteDto = new FriendInviteDto(playerFriendInvite,message);

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE,RabbitConfig.ROUTING_KEY, friendInviteDto);
    }

}

package pl.rokolujka.springreactludo.playerGameInvite;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;
import pl.rokolujka.springreactludo.rabbitMQ.FriendInviteNotification;
import pl.rokolujka.springreactludo.rabbitMQ.GameInviteNotification;
import pl.rokolujka.springreactludo.rabbitMQ.RabbitConfig;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerGameInviteService {

    @Autowired
    private RabbitTemplate template;

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

    public void sendGameInvite(PlayerGameInvite playerGameInvite){

        createPlayerGameInvite(playerGameInvite);

        String message = "Player ["+playerGameInvite.getInvitingUserId()+"] sent game invite to player ["+ playerGameInvite.getInvitedUserId() +"]";
        GameInviteNotification gameInviteNotification = new GameInviteNotification(playerGameInvite,message);

        template.convertAndSend(RabbitConfig.EXCHANGE,RabbitConfig.ROUTING_KEY, gameInviteNotification);

    }
}

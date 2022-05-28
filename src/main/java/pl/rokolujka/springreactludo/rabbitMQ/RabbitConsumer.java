package pl.rokolujka.springreactludo.rabbitMQ;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInviteService;

@Component
public class RabbitConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final PlayerFriendInviteService playerFriendInviteService;

    @Autowired
    public RabbitConsumer(SimpMessagingTemplate simpMessagingTemplate,
                          PlayerFriendInviteService playerFriendInviteService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.playerFriendInviteService = playerFriendInviteService;
    }
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeFriendRequestFromQueue(FriendInviteDto friendInviteDto){
        System.out.println(friendInviteDto.getMessage());
        playerFriendInviteService.createPlayerFriendInvite(friendInviteDto.getPlayerFriendInvite());
        simpMessagingTemplate.convertAndSend("/friend_request", friendInviteDto);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(GameInviteDto gameInviteDto){
        System.out.println(gameInviteDto.getMessage());
    }

}

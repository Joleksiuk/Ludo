package pl.rokolujka.springreactludo.rabbitMQ;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public RabbitConsumer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(FriendInviteNotification friendInviteNotification){
        System.out.println(friendInviteNotification.getMessage());
        simpMessagingTemplate.convertAndSend("/player_friend_request", friendInviteNotification);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(GameInviteNotification gameInviteNotification){
        System.out.println(gameInviteNotification.getMessage());
    }

}

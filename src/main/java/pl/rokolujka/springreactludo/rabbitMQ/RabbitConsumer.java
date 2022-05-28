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
    public void consumeFriendRequestFromQueue(FriendInviteDto friendInviteDto){
        System.out.println(friendInviteDto.getMessage());
        simpMessagingTemplate.convertAndSend("/friend_request", friendInviteDto);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(GameInviteDto gameInviteDto){
        System.out.println(gameInviteDto.getMessage());
    }

}

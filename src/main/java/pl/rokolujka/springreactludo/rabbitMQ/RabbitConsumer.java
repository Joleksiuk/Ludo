package pl.rokolujka.springreactludo.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(FriendInviteNotification friendInviteNotification){
        System.out.println(friendInviteNotification.getMessage());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(GameInviteNotification gameInviteNotification){
        System.out.println(gameInviteNotification.getMessage());
    }
}

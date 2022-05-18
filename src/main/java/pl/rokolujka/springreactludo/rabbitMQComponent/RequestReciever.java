package pl.rokolujka.springreactludo.rabbitMQComponent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RequestReciever {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void consumeMessageFromQueue(FriendInviteStatus friendInviteStatus){
        System.out.println("Message received from queue : "+ friendInviteStatus );
    }
}

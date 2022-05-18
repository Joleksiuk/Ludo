package pl.rokolujka.springreactludo.rabbitMQComponent;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendRequester {

    @Autowired
    private RabbitTemplate template;

    @PostMapping("/{requester}")
    public String friendRequest(@RequestBody FriendInvite friendInvite,@PathVariable String requester){
        friendInvite.setInvitationId(UUID.randomUUID().toString());
        FriendInviteStatus friendInviteStatus = new FriendInviteStatus(friendInvite,"PROCESS",
                "Friend request sent succesfully from "+ requester);
        template.convertAndSend(RabbitConfig.EXCHANGE,RabbitConfig.ROUTING_KEY,friendInviteStatus);
        return "Success!";
    }

}

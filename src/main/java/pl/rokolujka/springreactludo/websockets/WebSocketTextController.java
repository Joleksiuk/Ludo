package pl.rokolujka.springreactludo.websockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send_friend_invite")
    public ResponseEntity<Void> sendInvite(@RequestBody InviteDto frindInvitedDto) {
        template.convertAndSend("/friend_invite_dupa", frindInvitedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/receive_friend_invite")
    public void receiveInvite(@Payload  InviteDto textMessageDTO) {
        // receive message from client
    }

    @SendTo("/topic/friend_invite")
    public  InviteDto broadcastInvite(@Payload  InviteDto textMessageDTO) {
        return textMessageDTO;
    }
}
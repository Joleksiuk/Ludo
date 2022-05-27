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

    @PostMapping("/qqqqqqqqqqqqqqqqqqq")
    public ResponseEntity<Void> sendInvite(@RequestBody InviteDto frindInvitedDto) {
        template.convertAndSend("/qqqqq", frindInvitedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/qqqqqqqqqqqqq")
    public void receiveInvite(@Payload  InviteDto textMessageDTO) {
        // receive message from client
    }

    @SendTo("/qqqqqqq")
    public  InviteDto broadcastInvite(@Payload  InviteDto textMessageDTO) {
        return textMessageDTO;
    }
}
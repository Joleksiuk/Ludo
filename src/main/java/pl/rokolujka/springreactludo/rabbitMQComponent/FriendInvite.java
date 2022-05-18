package pl.rokolujka.springreactludo.rabbitMQComponent;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendInvite {

    private String invitationId;
    private Integer invitingUserId;
    private Integer invitedUserId;
}

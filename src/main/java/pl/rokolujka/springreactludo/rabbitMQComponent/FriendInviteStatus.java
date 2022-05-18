package pl.rokolujka.springreactludo.rabbitMQComponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendInviteStatus {
    private FriendInvite friendInvite;
    private String status;
    private String message;
}

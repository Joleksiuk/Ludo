package pl.rokolujka.springreactludo.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.rokolujka.springreactludo.playerFriendInvite.PlayerFriendInvite;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FriendInviteDto {
    private PlayerFriendInvite playerFriendInvite;
    private String message;
}

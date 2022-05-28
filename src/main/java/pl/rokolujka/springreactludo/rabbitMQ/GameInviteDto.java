package pl.rokolujka.springreactludo.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.rokolujka.springreactludo.playerGameInvite.PlayerGameInvite;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameInviteDto {
    private PlayerGameInvite playerGameInvite;
    private String message;
}

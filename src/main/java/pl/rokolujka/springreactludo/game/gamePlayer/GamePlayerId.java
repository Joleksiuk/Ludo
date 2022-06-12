package pl.rokolujka.springreactludo.game.gamePlayer;

import lombok.*;
import pl.rokolujka.springreactludo.game.pawn.Pawn;

import java.io.Serializable;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GamePlayerId implements Serializable {
    private Integer playerId;
    private Integer gameId;
}

package pl.rokolujka.springreactludo.game.pawn;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PawnId implements Serializable {
    Integer number;
    Integer playerId;
    Integer gameId;
}

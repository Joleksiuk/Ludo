package pl.rokolujka.springreactludo.game.pawn;

import lombok.*;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerId;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pawn")
@IdClass(PawnId.class)
@Getter
public class Pawn {

    @Id
    private Integer number;
    @Id
    private Integer playerId;
    @Id
    private Integer gameId;

    private Integer progress;
    private Boolean didStart;

    @Transient
    Integer fieldId;

    public void move(Integer diceValue) {
        if (didStart) {
            progress += diceValue;
        } else {
            didStart = true;
        }
    }

    public Pawn clone() {
        return new Pawn(number, playerId, gameId, progress, didStart, fieldId);
    }

    public void reset() {
        progress = 0;
        didStart = false;
    }

    public GamePlayerId getGamePlayerId() {
        return GamePlayerId.builder()
                .gameId(getGameId())
                .playerId(getPlayerId())
                .build();
    }
}

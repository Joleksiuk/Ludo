package pl.rokolujka.springreactludo.game.gamePlayer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

@EqualsAndHashCode
@Builder
@Value
public class GamePlayerId implements Serializable {
    Integer userId;
    Integer gameId;
}

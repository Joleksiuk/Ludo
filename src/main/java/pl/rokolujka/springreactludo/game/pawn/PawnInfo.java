package pl.rokolujka.springreactludo.game.pawn;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PawnInfo {
    Integer number;
    Integer fieldId;
    String color;
}

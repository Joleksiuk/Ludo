package pl.rokolujka.springreactludo.webSocket;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GameStatusMessage {
    Integer diceValue;
}

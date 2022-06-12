package pl.rokolujka.springreactludo.webSocket;

import lombok.Builder;
import lombok.Value;


@Builder
@Value
public class LobbyStatusMessage {
    Integer playerId;
    String playerColour;
    boolean gameStarted;
}

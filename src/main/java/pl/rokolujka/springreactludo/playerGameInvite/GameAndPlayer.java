package pl.rokolujka.springreactludo.playerGameInvite;

import lombok.AllArgsConstructor;
import lombok.Value;
import pl.rokolujka.springreactludo.game.Game;
import pl.rokolujka.springreactludo.player.Player;

@AllArgsConstructor
@Value
public class GameAndPlayer {
    Game game;
    Player player;
}

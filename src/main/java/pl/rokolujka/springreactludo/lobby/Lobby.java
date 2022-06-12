package pl.rokolujka.springreactludo.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.rokolujka.springreactludo.player.Player;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class Lobby {

    Map<Integer,String> mapColorToPlayer;
    List<Player> players;
}

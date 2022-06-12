package pl.rokolujka.springreactludo.lobby;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerService;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LobbyService {

    private final GamePlayerService gamePlayerService;
    private final PlayerService playerService;

    Lobby findLobbyByGameId(Integer gameId){
        List<GamePlayer> gamePlayers = gamePlayerService.findAllGamePlayersByGameId(gameId);
        List<Player> players = gamePlayers.stream()
                .map(gamePlayer -> playerService.findPlayerById(gamePlayer.getPlayerId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        Map<Integer,String> mapPlayerToColor = players.stream()
                .collect(Collectors.toMap(
                        Player::getId,
                        player -> getPlayerColor(gamePlayers,player)
                ));
        return new Lobby(mapPlayerToColor,players);
    }

    String getPlayerColor(List<GamePlayer> gamePlayers, Player player){
        return gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getPlayerId().equals(player.getId()))
                .map(GamePlayer::getPlayerColour)
                .findFirst()
                .orElseThrow();
    }
}

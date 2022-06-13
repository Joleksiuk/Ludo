package pl.rokolujka.springreactludo.lobby;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.game.board.Board;
import pl.rokolujka.springreactludo.game.board.BoardEnum;
import pl.rokolujka.springreactludo.game.board.BoardService;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerService;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerService;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LobbyService {

    private final GamePlayerService gamePlayerService;
    private final PlayerService playerService;
    private final GameService gameService;
    private final BoardService boardService;

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

    List<LobbyModel> getLobbyParticipantsByGameId(Integer gameId){
        Lobby lobby = findLobbyByGameId(gameId);
        List<LobbyModel> lobbyParticipants = new ArrayList<>();
        lobby.players.forEach(
                player -> {
                    String color = lobby.mapColorToPlayer.get(player.getId());
                    LobbyModel lobbyModel = new LobbyModel(player.getId(), color, player.getNickname(), player.getPicture());
                    lobbyParticipants.add(lobbyModel);
                }
        );
        return lobbyParticipants;
    }

    List<String> getAvailableBoardColorsByGameId(Integer gameId){
        BoardEnum board = gameService.getGameBoardByGameId(gameId);
        if(board.getMaxPlayers()==2){
            return Arrays.asList("red", "green");
        }
        return Arrays.asList("red", "green", "blue", "yellow");
    }

}

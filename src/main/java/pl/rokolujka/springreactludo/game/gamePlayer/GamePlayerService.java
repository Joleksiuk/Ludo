package pl.rokolujka.springreactludo.game.gamePlayer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.GameService;
import pl.rokolujka.springreactludo.game.board.BoardDefaultTurnOrderEnum;
import pl.rokolujka.springreactludo.game.board.ColorEnum;
import pl.rokolujka.springreactludo.playerGameInvite.PlayerGameInvite;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GamePlayerService {
    private final GamePlayerRepository gamePlayerRepository;
    private final GameService gameService;

    public void createPlayerGameInvite(GamePlayer gamePlayer) {
        gamePlayerRepository.save(gamePlayer);
    }

    public void deletePlayerGameInvite(GamePlayer gamePlayer) {
        gamePlayerRepository.delete(gamePlayer);
    }

    public List<GamePlayer> findAllGamePlayers() {
        List<GamePlayer> gamePlayers = new LinkedList<>();
        gamePlayerRepository.findAll().forEach(gamePlayers::add);
        return gamePlayers;
    }

    public void updateGamePlayer(GamePlayer gamePlayer){
        if(gamePlayer.getPlayerId()==null || gamePlayer.getGameId()==null){
            throw new IllegalArgumentException("incorrect game player parameters");
        }
        gamePlayerRepository.save(gamePlayer);
    }

    public List<GamePlayer> findAllGamePlayersByGameId(Integer gameId){
        return gamePlayerRepository.findAllGamePLayersByGameId(gameId);
    }

    public void createFromGameInvite(PlayerGameInvite invite) {
        GamePlayer gamePlayer = new GamePlayer(invite.getInvitedPlayerId(), invite.getGameId(),
                gameService.findFirstFreeColor(invite.getGameId()).getColor(), null);
        gamePlayerRepository.save(gamePlayer);
    }
}

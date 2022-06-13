package pl.rokolujka.springreactludo.game.gamePlayer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.playerGameInvite.PlayerGameInvite;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GamePlayerService {
    private final GamePlayerRepository gamePlayerRepository;

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
        GamePlayer gamePlayer = new GamePlayer(invite.getInvitedPlayerId(), invite.getGameId(), null, null);
        gamePlayerRepository.save(gamePlayer);
    }
}

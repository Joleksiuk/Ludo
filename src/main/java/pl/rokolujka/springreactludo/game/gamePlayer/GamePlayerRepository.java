package pl.rokolujka.springreactludo.game.gamePlayer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GamePlayerRepository extends CrudRepository<GamePlayer, GamePlayerId> {

    List<GamePlayer> findByGameId(Integer gameId);
    List<GamePlayer> findAllGamePLayersByGameId(Integer gameId);
    List<GamePlayer> findByPlayerId(Integer playerId);
}

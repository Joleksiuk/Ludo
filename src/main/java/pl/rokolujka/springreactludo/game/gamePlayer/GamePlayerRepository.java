package pl.rokolujka.springreactludo.game.gamePlayer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, GamePlayerId> {

    List<GamePlayer> findByGameId(Integer gameId);
}

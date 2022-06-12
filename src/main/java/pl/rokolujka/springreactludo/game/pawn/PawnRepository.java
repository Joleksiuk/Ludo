package pl.rokolujka.springreactludo.game.pawn;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PawnRepository extends JpaRepository<Pawn, PawnId> {
    List<Pawn> findByGameId(Integer gameId);

    List<Pawn> findByGameIdAndPlayerId(Integer gameId, Integer playerId);
}

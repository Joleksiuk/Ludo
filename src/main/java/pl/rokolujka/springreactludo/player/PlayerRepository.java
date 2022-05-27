package pl.rokolujka.springreactludo.player;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Optional<Player> findByNickname(String nickname);
    Boolean existsByNickname(String nickname);
}

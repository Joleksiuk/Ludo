package pl.rokolujka.springreactludo.game;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findGameById(Integer id);
}

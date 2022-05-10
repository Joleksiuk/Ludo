package pl.rokolujka.springreactludo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> findAllGames() {
        List<Game> games = new LinkedList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public void createGame(Game game) {
        gameRepository.save(game);
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGameById(Integer id) {
        gameRepository.deleteById(id);
    }
}

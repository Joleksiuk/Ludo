package pl.rokolujka.springreactludo.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> findAllPlayers() {
        List<Player> players = new LinkedList<>();
        playerRepository.findAll().forEach(players::add);
        return players;
    }

    public void createPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayerById(Integer id) {
        playerRepository.deleteById(id);
    }

    public Optional<Player> findPlayerByNickname(String nickname){
        return playerRepository.findByNickname(nickname);
    }
}

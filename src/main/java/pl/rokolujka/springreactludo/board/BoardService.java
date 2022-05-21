package pl.rokolujka.springreactludo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> findAllOrderByMaxPlayers() {
        return boardRepository.findByOrderByMaxPlayersAsc();

    }
}


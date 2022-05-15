package pl.rokolujka.springreactludo.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @RequestMapping("boards")
    public List<Board> getBoards() {
        return boardService.findAllOrderByMaxPlayers();
    }
}

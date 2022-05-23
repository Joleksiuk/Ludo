package pl.rokolujka.springreactludo.game.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping("boards")
    public List<Board> getBoards() {
        return boardService.findAllOrderByMaxPlayers();
    }
}

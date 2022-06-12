package pl.rokolujka.springreactludo.game.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static pl.rokolujka.springreactludo.game.board.ColorEnum.*;

@AllArgsConstructor
@Getter
public enum BoardDefaultTurnOrderEnum {

    SMALL_BOARD_ORDER(BoardEnum.SMALL, List.of(RED, GREEN)),
    STANDARD_BOARD_ORDER(BoardEnum.STANDARD, List.of(RED, BLUE, GREEN, YELLOW));

    private final BoardEnum board;
    private final List<ColorEnum> colorTurnOrder;

    public static BoardDefaultTurnOrderEnum getByBoard(BoardEnum board) {
        return Stream.of(BoardDefaultTurnOrderEnum.values())
                .filter(orderEnum -> orderEnum.getBoard().equals(board))
                .findFirst()
                .orElseThrow();

    }
}

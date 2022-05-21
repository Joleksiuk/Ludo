package pl.rokolujka.springreactludo.game.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum BoardEnum {

    STANDARD("4player_board.csv", "4-player board", "standard", 4, 11, 13),
    SMALL("2player_board.csv", "2-player board", "small", 2, 10, 13);

    private final String fileName;
    private final String name;
    private final String code;
    private final Integer maxPlayers;
    private final Integer rows;
    private final Integer columns;

    public static BoardEnum getByCode(String code) {
        return Stream.of(BoardEnum.values())
                .filter(boardEnum -> boardEnum.getCode().equals(code))
                .findFirst()
                .orElseThrow();
    }
}

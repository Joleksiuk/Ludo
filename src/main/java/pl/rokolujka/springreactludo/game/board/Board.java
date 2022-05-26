package pl.rokolujka.springreactludo.game.board;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board {

    private String code;
    private String name;
    private Integer maxPlayers;

    public static Board fromBoardEnum(BoardEnum boardEnum) {
        return new Board(boardEnum.getCode(), boardEnum.getName(), boardEnum.getMaxPlayers());
    }
}

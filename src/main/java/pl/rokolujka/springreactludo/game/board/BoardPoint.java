package pl.rokolujka.springreactludo.game.board;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class BoardPoint {

    @Getter
    private final Integer id;
    private final Integer rowSize;

    private BoardPoint(Integer id, Integer rowSize) {
        this.id = id;
        this.rowSize = rowSize;
    }

    public static BoardPoint fromId(Integer id, BoardEnum board) {
        return new BoardPoint(id, board.getColumns());
    }

    public static BoardPoint fromCoordinates(Integer x, Integer y,BoardEnum board) {
        return new BoardPoint(x + board.getColumns() * y, board.getColumns());
    }

    public Integer getX() {
        return id % rowSize;
    }

    public Integer getY() {
        return id / rowSize;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.getX(), this.getY());
    }
}

package pl.rokolujka.springreactludo.game.board;

import java.util.List;

public interface BoardPath {
    List<BoardPoint> getPathPoints(ColorEnum color);
}

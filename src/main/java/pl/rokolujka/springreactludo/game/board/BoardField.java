package pl.rokolujka.springreactludo.game.board;


import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class BoardField {
    String color;
    boolean empty;
    Integer id;

    public static int getId(int x, int y, int columns) {
        return y * columns + x;
    }
}

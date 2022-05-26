package pl.rokolujka.springreactludo.game.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum ColorEnum {

    RED("red", "r"),
    GREEN("green", "g"),
    BLUE("blue","b"),
    YELLOW("yellow", "y");

    private final String color;
    private final String code;

    public static ColorEnum getByColorName(String colorName) {
        return Stream.of(ColorEnum.values())
                .filter(color -> color.getColor().equals(colorName))
                .findFirst()
                .orElseThrow();
    }
}

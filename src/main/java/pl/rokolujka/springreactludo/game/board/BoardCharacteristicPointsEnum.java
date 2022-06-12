package pl.rokolujka.springreactludo.game.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum BoardCharacteristicPointsEnum {

    STANDARD_POINTS(
            BoardEnum.STANDARD,
            Map.of(
                    ColorEnum.YELLOW, BoardPoint.fromCoordinates(1, 4, BoardEnum.STANDARD),
                    ColorEnum.RED, BoardPoint.fromCoordinates(7, 0, BoardEnum.STANDARD),
                    ColorEnum.BLUE, BoardPoint.fromCoordinates(11, 6, BoardEnum.STANDARD),
                    ColorEnum.GREEN, BoardPoint.fromCoordinates(5, 10, BoardEnum.STANDARD)
            ),
            Map.of(
                    ColorEnum.YELLOW, BoardPoint.fromCoordinates(5, 5, BoardEnum.STANDARD),
                    ColorEnum.RED, BoardPoint.fromCoordinates(6, 4, BoardEnum.STANDARD),
                    ColorEnum.BLUE, BoardPoint.fromCoordinates(7, 5, BoardEnum.STANDARD),
                    ColorEnum.GREEN, BoardPoint.fromCoordinates(6, 6, BoardEnum.STANDARD)
            ),
            Map.of(
                    ColorEnum.YELLOW, BoardPoint.fromCoordinates(2, 1, BoardEnum.STANDARD),
                    ColorEnum.RED, BoardPoint.fromCoordinates(9, 1, BoardEnum.STANDARD),
                    ColorEnum.BLUE, BoardPoint.fromCoordinates(9, 8, BoardEnum.STANDARD),
                    ColorEnum.GREEN, BoardPoint.fromCoordinates(2, 8, BoardEnum.STANDARD)
            ),
            List.of(
                    BoardPoint.fromCoordinates(5, 0, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(7, 0, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(7, 4, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(11, 4, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(11, 6, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(7, 6, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(7, 10, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(5, 10, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(5, 6, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(1, 6, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(1, 4, BoardEnum.STANDARD),
                    BoardPoint.fromCoordinates(5, 4, BoardEnum.STANDARD)
            )
    ),
    SMALL_POINTS(
            BoardEnum.SMALL,
            Map.of(
                    ColorEnum.RED, BoardPoint.fromCoordinates(7, 0, BoardEnum.SMALL),
                    ColorEnum.GREEN, BoardPoint.fromCoordinates(5, 9, BoardEnum.SMALL)
            ),
            Map.of(
                   ColorEnum.RED, BoardPoint.fromCoordinates(6, 4, BoardEnum.SMALL),
                   ColorEnum.GREEN, BoardPoint.fromCoordinates(6, 5, BoardEnum.SMALL)
            ),
            Map.of(
                    ColorEnum.RED, BoardPoint.fromCoordinates(9, 1, BoardEnum.SMALL),
                    ColorEnum.GREEN, BoardPoint.fromCoordinates(2, 7, BoardEnum.SMALL)
            ),
            List.of(
                    BoardPoint.fromCoordinates(5, 0, BoardEnum.SMALL),
                    BoardPoint.fromCoordinates(7, 0, BoardEnum.SMALL),
                    BoardPoint.fromCoordinates(7, 9, BoardEnum.SMALL),
                    BoardPoint.fromCoordinates(5, 9, BoardEnum.SMALL)
            )
    );

    private final BoardEnum boardEnum;
    private final Map<ColorEnum, BoardPoint> startingPoints;
    private final Map<ColorEnum, BoardPoint> pitEndingPoints;
    private final Map<ColorEnum, BoardPoint> topLeftOfStartingBay;
    private final List<BoardPoint> breakPoints;

    public static BoardCharacteristicPointsEnum fromBoardEnum(BoardEnum board) {
        return Stream.of(BoardCharacteristicPointsEnum.values())
                .filter(pointsEnum -> pointsEnum.getBoardEnum().equals(board))
                .findFirst()
                .orElseThrow();
    }
    public static List<ColorEnum> getColorsOfBoard(BoardEnum boardEnum){
        return new ArrayList<>(fromBoardEnum(boardEnum).getStartingPoints().keySet());

    }
}

package pl.rokolujka.springreactludo.game.board;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StartingBayPath implements BoardPath {

    private final BoardEnum board;

    @Override
    public List<BoardPoint> getPathPoints(ColorEnum color) {
        BoardPoint topLeft =  BoardCharacteristicPointsEnum.fromBoardEnum(board)
                .getTopLeftOfStartingBay()
                .get(color);

        return List.of(
                topLeft,
                BoardPoint.fromCoordinates(topLeft.getX() + 1, topLeft.getY(), board),
                BoardPoint.fromCoordinates(topLeft.getX(), topLeft.getY() + 1, board),
                BoardPoint.fromCoordinates(topLeft.getX() + 1, topLeft.getY() + 1, board)
        );

    }
}

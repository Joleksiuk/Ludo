package pl.rokolujka.springreactludo.game.board;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class MainPath implements BoardPath {

    private final BoardEnum board;

    @Override
    public List<BoardPoint> getPathPoints(ColorEnum color) {
        BoardCharacteristicPointsEnum characteristicPoints = BoardCharacteristicPointsEnum.fromBoardEnum(board);
        BoardPoint startingPoint = characteristicPoints.getStartingPoints().get(color);
        BoardPoint endingPoint = characteristicPoints.getPitEndingPoints().get(color);
        List<BoardPoint> breakPoints = characteristicPoints.getBreakPoints();

        List<BoardPoint> reordered = reorderPoints(breakPoints, startingPoint);
        BoardPoint lastPointBeforeStarting = reordered.get(reordered.size() - 1);
        BoardPoint pitEntry = getPointsBetween(lastPointBeforeStarting, startingPoint)
                .stream()
                .filter(point -> !point.equals(startingPoint) && !point.equals(lastPointBeforeStarting))
                .findFirst()
                .orElseThrow();
        reordered.add(pitEntry);
        reordered.add(endingPoint);

        return getAllPointsInBetween(reordered);
    }

    private List<BoardPoint> getPointsBetween(BoardPoint p1, BoardPoint p2) {
        if (p1.getX().equals(p2.getX())) {
            return getPointsInDirection(p1.getX(), p1.getY(), p2.getY(), true);
        } else if (p1.getY().equals(p2.getY())) {
            return getPointsInDirection(p1.getY(), p1.getX(), p2.getX(), false);
        }
        throw new IllegalStateException("Invalid path. Points should be on a x or y line." +
                String.format("%s, %s",p1, p2));
    }

    private List<BoardPoint> getPointsInDirection(int commonCoordinate, int c1, int c2, boolean isCommonX) {

        List<BoardPoint> points = IntStream.range(Math.min(c1, c2), Math.max(c1, c2) + 1)
                .mapToObj(coordinate -> BoardPoint.fromCoordinates(
                        isCommonX ? commonCoordinate : coordinate,
                        isCommonX ? coordinate : commonCoordinate,
                        board))
                .collect(Collectors.toList());

        if (c1 > c2) {
            points.sort(Comparator.comparing(isCommonX ? BoardPoint::getY : BoardPoint::getX).reversed());
        }
        return points;
    }

    private List<BoardPoint> reorderPoints(List<BoardPoint> boardPoints, BoardPoint start) {
        int indexOfStarting = boardPoints.indexOf(start);
        if (indexOfStarting == -1) {
            throw new IllegalArgumentException(String.format("Could not reorder points. Point %s is not a breakpoint.", start));
        }
        List<BoardPoint> reordered = new LinkedList<>(boardPoints.subList(indexOfStarting, boardPoints.size()));
        reordered.addAll(boardPoints.subList(0, indexOfStarting));
        return reordered;
    }

    private List<BoardPoint> getAllPointsInBetween(List<BoardPoint> points) {
        List<BoardPoint> allPoints = new LinkedList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            allPoints.addAll(getPointsBetween(points.get(i), points.get(i + 1)));
        }
        return allPoints.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}

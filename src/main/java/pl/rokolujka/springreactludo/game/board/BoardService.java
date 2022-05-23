package pl.rokolujka.springreactludo.game.board;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.rokolujka.springreactludo.game.pawn.Pawn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class BoardService {

    private static final String BOARD_DELIMITER = ",";

    private final BoardLayoutLoader boardLayoutLoader;

    public List<Board> findAllOrderByMaxPlayers() {
        return Stream.of(BoardEnum.values())
                .map(Board::fromBoardEnum)
                .collect(Collectors.toList());
    }

    public List<List<BoardField>> getFieldMatrixByBoard(BoardEnum board) {
        List<List<BoardField>> listOfRows;
        Resource boardLayoutResource = boardLayoutLoader.loadBoardLayout(board);
        try {
            File configFile = boardLayoutResource.getFile();
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            listOfRows = getAllRows(reader, board);
        } catch (IOException e) {
            throw new IllegalStateException("Could not load board file");
        }

        return listOfRows;
    }

    public Integer getStartingFieldIdForPawn(Integer pawnNumber, BoardEnum board, ColorEnum color) {
        StartingBayPath path = new StartingBayPath(board);
        return path.getPathPoints(color).get(pawnNumber).getId();
    }

    public Integer getFieldIdForPawn(BoardEnum board, Pawn pawn, ColorEnum color) {
        if (!pawn.getDidStart()) {
            return getStartingFieldIdForPawn(pawn.getNumber(), board, color);
        }

        MainPath mainPath = new MainPath(board);
        return mainPath.getPathPoints(color).get(pawn.getProgress()).getId();
    }

     private List<List<BoardField>> getAllRows(BufferedReader reader, BoardEnum board) throws IOException {
        List<List<BoardField>> listOfRows = new ArrayList<>();
        String currentLine = reader.readLine();
        int rows = 0;
        while (currentLine != null) {
            listOfRows.add(getRow(currentLine, board, rows++));
            currentLine = reader.readLine();
        }
        if (rows != board.getRows()) {
            throw new IllegalStateException("Invalid number of columns");
        }
        return listOfRows;
    }

    private List<BoardField> getRow(String currentLine, BoardEnum board, int rowNumber) {
        int columnNumber = 0;
        List<BoardField> row = new LinkedList<>();
        for (String code : currentLine.split(BOARD_DELIMITER)) {
             row.add(createField(code, rowNumber, columnNumber++, board.getColumns()));
        }
        if (row.size() != board.getColumns()) {
            throw new IllegalStateException("Invalid number of rows");
        }
        return row;
    }

    private Optional<BoardEnum> findBoardByNumberOfPlayers(Integer numberOfPlayers) {
        return Stream.of(BoardEnum.values())
                .filter(boardEnum -> numberOfPlayers <= boardEnum.getMaxPlayers())
                .min(Comparator.comparingInt(BoardEnum::getMaxPlayers));
    }

    private BoardField createField(String code, int rowNumber, int columnNumber, int columns) {
        String color;
        boolean isEmpty = StringUtils.startsWithIgnoreCase(code, "-");
        if (code.equals("-") || code.equals("*")) {
            color = "blank";
        } else {
            color = Stream.of(ColorEnum.values())
                    .filter(pawnColor -> pawnColor.getCode().equals(code.substring(1)))
                    .map(ColorEnum::getColor)
                    .findAny()
                    .orElseThrow();
        }
        return BoardField.builder()
                .empty(isEmpty)
                .color(color)
                .id(BoardField.getId(columnNumber, rowNumber, columns))
                .build();
    }
}


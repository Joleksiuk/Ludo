package pl.rokolujka.springreactludo.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.board.BoardEnum;
import pl.rokolujka.springreactludo.game.board.BoardField;
import pl.rokolujka.springreactludo.game.board.BoardService;
import pl.rokolujka.springreactludo.game.board.ColorEnum;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerId;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerRepository;
import pl.rokolujka.springreactludo.game.pawn.Pawn;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;
import pl.rokolujka.springreactludo.game.pawn.PawnRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GameService {

    private final GamePlayerRepository gamePlayerRepository;
    private final PawnRepository pawnRepository;
    private final GameRepository gameRepository;
    private final BoardService boardService;

    private static final int PAWNS_PER_COLOUR = 4;

    @Autowired
    public GameService(GamePlayerRepository gamePlayerRepository,
                       PawnRepository pawnRepository,
                       GameRepository gameRepository,
                       BoardService boardService) {
        this.gamePlayerRepository = gamePlayerRepository;
        this.pawnRepository = pawnRepository;
        this.gameRepository = gameRepository;
        this.boardService = boardService;
    }

    public List<Game> findAllGames() {
        List<Game> games = new LinkedList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public void createGame(Game game) {
        gameRepository.save(game);
        // FIXME CTAI-20 Create pawns after the game start when player_game are all already saved
        List<GamePlayer> gamePlayers = List.of(
                new GamePlayer(1, game.getId(), ColorEnum.RED.getColor()),
                new GamePlayer(2, game.getId(), ColorEnum.GREEN.getColor()),
                new GamePlayer(3, game.getId(), ColorEnum.BLUE.getColor()),
                new GamePlayer(4, game.getId(), ColorEnum.YELLOW.getColor())
                );
        gamePlayerRepository.saveAll(BoardEnum.getByCode(game.getBoardCode()).equals(BoardEnum.SMALL)
                ? gamePlayers.subList(0, 2)
                : gamePlayers);
        pawnRepository.saveAll(createGamePawns(game));
    }

    private List<Pawn> createGamePawns(Game game) {
        return findGamePlayersByGame(game).stream()
                .map(gamePlayer -> createGamePlayerPawns(gamePlayer, game))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Pawn> createGamePlayerPawns(GamePlayer gamePlayer, Game game) {
        BoardEnum board = BoardEnum.getByCode(game.getBoardCode());
        ColorEnum color = ColorEnum.getByColorName(gamePlayer.getPlayerColour());
        return IntStream.range(0, PAWNS_PER_COLOUR)
                .mapToObj(number -> Pawn.builder()
                        .number(number)
                        .userId(gamePlayer.getUserId())
                        .gameId(gamePlayer.getGameId())
                        .progress(0)
                        .fieldId(boardService.getStartingFieldIdForPawn(number, board, color))
                        .build())
                .collect(Collectors.toList());
    }

    private List<GamePlayer> findGamePlayersByGame(Game game) {
        return gamePlayerRepository.findByGameId(game.getId());
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGameById(Integer id) {
        gameRepository.deleteById(id);
    }

    public List<PawnInfo> findGamePawnsById(Integer gameId) {
        return pawnRepository.findByGameId(gameId)
                .stream()
                .map(this::mapPawnToPawnInfo)
                .collect(Collectors.toList());
    }

    private PawnInfo mapPawnToPawnInfo(Pawn pawn) {
        ColorEnum pawnColor = getPawnColor(pawn);
        return PawnInfo.builder()
                .fieldId(boardService.getFieldIdForPawn(getGameBoardByGameId(pawn.getGameId()), pawn, pawnColor))
                .color(pawnColor.getColor())
                .build();
    }

    private BoardEnum getGameBoardByGameId(Integer gameId) {
        return gameRepository.findById(gameId)
                .map(Game::getBoardCode)
                .map(BoardEnum::getByCode)
                .orElseThrow();
    }

    private ColorEnum getPawnColor(Pawn pawn) {
        GamePlayerId gamePlayerId = GamePlayerId.builder()
                .gameId(pawn.getGameId())
                .userId(pawn.getUserId())
                .build();
        return gamePlayerRepository.findById(gamePlayerId)
                .map(GamePlayer::getPlayerColour)
                .map(ColorEnum::getByColorName)
                .orElseThrow();
    }

    public List<List<BoardField>> getGameFieldMatrixById(Integer gameId) {
        return boardService.getFieldMatrixByBoard(getGameBoardByGameId(gameId));
    }
}

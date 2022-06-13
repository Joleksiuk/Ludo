package pl.rokolujka.springreactludo.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.rokolujka.springreactludo.game.board.*;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayer;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerId;
import pl.rokolujka.springreactludo.game.gamePlayer.GamePlayerRepository;
import pl.rokolujka.springreactludo.game.pawn.Pawn;
import pl.rokolujka.springreactludo.game.pawn.PawnInfo;
import pl.rokolujka.springreactludo.game.pawn.PawnRepository;
import pl.rokolujka.springreactludo.lobby.LobbyModel;
import pl.rokolujka.springreactludo.player.Player;
import pl.rokolujka.springreactludo.player.PlayerService;

import java.awt.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GamePlayerRepository gamePlayerRepository;
    private final PawnRepository pawnRepository;
    private final GameRepository gameRepository;
    private final BoardService boardService;
    private final PlayerService playerService;

    private static final int PAWNS_PER_COLOUR = 4;
    private static final int DICE_MAX = 6;
    private static final int NO_PROGRESS = 0;


    public List<Game> findAllGames() {
        List<Game> games = new LinkedList<>();
        gameRepository.findAll().forEach(games::add);
        return games;
    }

    public void createGame(Game game, String nickname) {
        Player player = playerService.findPlayerByNickname(nickname).orElseThrow();
        game.setTurnPlayerId(player.getId());
        game.setCurrentDiceValue(1);
        game.setDiceThrownInTurn(false);
        gameRepository.save(game);
        GamePlayer creatingGamePlayer = new GamePlayer(player.getId(), game.getId(), findFirstFreeColor(game.getId()).getColor(), null);
        gamePlayerRepository.save(creatingGamePlayer);
    }

    public ColorEnum findFirstFreeColor(Integer gameId) {
        List<String> occupiedColorCodes = gamePlayerRepository.findByGameId(gameId).stream()
                .map(GamePlayer::getPlayerColour)
                .collect(Collectors.toList());
        return getGameDefaultOrderColors(gameId)
                .stream()
                .filter(color -> !occupiedColorCodes.contains(color.getColor()))
                .findFirst()
                .orElseThrow();
    }

    private List<GamePlayer> createLobbyGamePlayers(Map<Integer, String> playerIdToColorMap, Integer gameId) {
        List<ColorEnum> colors = getGameDefaultOrderColors(gameId);
        List<GamePlayer> gamePlayers = new LinkedList<>();
        for (int i = 0; i < colors.size(); i++) {
            String currentColorValue = colors.get(i).getColor();
            String nextColorValue = colors.get(i + 1 < colors.size() ? i + 1 : 0).getColor();
            Integer currentPlayer = getPlayerIdFromColorMap(playerIdToColorMap, currentColorValue);
            Integer nextPlayer = getPlayerIdFromColorMap(playerIdToColorMap, nextColorValue);
            gamePlayers.add(new GamePlayer(currentPlayer, gameId, currentColorValue, nextPlayer));
        }
        return gamePlayers;
    }

    public List<ColorEnum> getGameDefaultOrderColors(Integer gameId) {
        return BoardDefaultTurnOrderEnum.getByBoard(getGameBoardByGameId(gameId)).getColorTurnOrder();
    }

    public void startGame(Game game, List<LobbyModel> lobbyModels){
        game.setTurnPlayerId(lobbyModels.stream().findFirst().orElseThrow().getPlayerId());
        gameRepository.save(game);

        Map<Integer,String> playerIdToColorMap=new HashMap<>();
        lobbyModels.forEach(player->{
            playerIdToColorMap.put(player.getPlayerId(), player.getColor());
        });
        gamePlayerRepository.saveAll(createLobbyGamePlayers(playerIdToColorMap,game.getId()));
        pawnRepository.saveAll(createGamePawns(game));

    }


    private Integer getPlayerIdFromColorMap(Map<Integer, String> playerIdToColor, String color) {
        return playerIdToColor.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(color))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
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
                        .playerId(gamePlayer.getPlayerId())
                        .gameId(gamePlayer.getGameId())
                        .didStart(false)
                        .progress(0)
                        .fieldId(boardService.getStartingBayFieldIdForPawn(number, board, color))
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
                .fieldId(getPawnFieldId(pawn))
                .color(pawnColor.getColor())
                .number(pawn.getNumber())
                .build();
    }

    public BoardEnum getGameBoardByGameId(Integer gameId) {
        return gameRepository.findById(gameId)
                .map(Game::getBoardCode)
                .map(BoardEnum::getByCode)
                .orElseThrow();
    }

    private ColorEnum getPawnColor(Pawn pawn) {
        GamePlayerId gamePlayerId = GamePlayerId.builder()
                .gameId(pawn.getGameId())
                .playerId(pawn.getPlayerId())
                .build();
        return getGamePlayerColour(gamePlayerId);
    }

    private ColorEnum getGamePlayerColour(GamePlayerId gamePlayerId) {
        return gamePlayerRepository.findById(gamePlayerId)
                .map(GamePlayer::getPlayerColour)
                .map(ColorEnum::getByColorName)
                .orElseThrow();
    }

    public List<List<BoardField>> getGameFieldMatrixById(Integer gameId) {
        return boardService.getFieldMatrixByBoard(getGameBoardByGameId(gameId));
    }

    public void setStartGameTimestamp(Integer id){
        Game game  = gameRepository.findGameById(id).orElse(null);
        if(game!=null){
            game.setStartDate(new Timestamp(System.currentTimeMillis()));
            gameRepository.save(game);
        }
    }

    public Game findGameById(Integer id){
        return gameRepository.findGameById(id).orElse(null);
    }

    public Player getGameTurn(Integer gameId) {
        return gameRepository.findById(gameId)
                .map(Game::getTurnPlayerId)
                .flatMap(playerService::findPlayerById)
                .orElseThrow();
    }

    public boolean isOtherPlayersTurn(String nickname, Integer gameId) {
        return playerService.findPlayerByNickname(nickname)
                .map(Player::getId)
                .map(playerId -> !getGameTurn(gameId).getId().equals(playerId))
                .orElseThrow();
    }

    public List<BoardMove> getPossibleMoves(String nickname, Integer gameId) {
        GamePlayerId gamePlayerId = toGamePlayerId(nickname, gameId);
        if (isOtherPlayersTurn(nickname, gameId)) {
            return List.of();
        }

        List<BoardMove> possibleMoves = new LinkedList<>(getMainMoves(gamePlayerId));

        if (getDiceValue(gameId).equals(DICE_MAX)) {
            possibleMoves.addAll(getStartingMoves(gamePlayerId));
        }

        return possibleMoves;
    }

    public Integer getDiceValue(Integer gameId) {
        return findById(gameId)
                .map(Game::getCurrentDiceValue)
                .orElseThrow();
    }

    private GamePlayerId toGamePlayerId(String nickname, Integer gameId) {
        return playerService.findPlayerByNickname(nickname)
                .map(Player::getId)
                .map(playerId -> GamePlayerId.builder()
                        .playerId(playerId)
                        .gameId(gameId)
                        .build())
                .orElseThrow();
    }

    private List<BoardMove> getStartingMoves(GamePlayerId gamePlayerId) {
        List<Pawn> playerPawns = pawnRepository.findByGameIdAndPlayerId(gamePlayerId.getGameId(), gamePlayerId.getPlayerId());

        if (playerPawns.stream().anyMatch(pawn -> pawn.getDidStart() && pawn.getProgress().equals(0))) {
            return List.of();
        }

        return playerPawns.stream()
                .filter(Predicate.not(Pawn::getDidStart))
                .map(this::getPawnFieldId)
                .map(fromFieldId ->
                        BoardMove.builder()
                                .fromFieldId(fromFieldId)
                                .toFieldId(getGamePlayerStartFieldId(gamePlayerId))
                                .build())
                .collect(Collectors.toList());
    }

    private List<BoardMove> getMainMoves(GamePlayerId gamePlayerId) {
        List<Pawn> playerPawns = pawnRepository.findByGameIdAndPlayerId(gamePlayerId.getGameId(), gamePlayerId.getPlayerId())
                .stream()
                .filter(Pawn::getDidStart)
                .collect(Collectors.toList());

        List<BoardMove> boardMoves = new LinkedList<>();

        for (Pawn pawn : playerPawns) {
            Pawn afterMove = pawn.clone();
            afterMove.move(getMoveProgressDifference(pawn));
            boolean conflict = pawnConflictAtProgress(gamePlayerId, afterMove.getProgress());
            if (conflict || didFinish(pawn)) {
                continue;
            }
            boardMoves.add(BoardMove.builder()
                    .fromFieldId(getPawnFieldId(pawn))
                    .toFieldId(getPawnFieldId(afterMove))
                    .build());
        }
        return boardMoves;
    }

    private Integer getMoveProgressDifference(Pawn pawn) {
        Integer gameId = pawn.getGameId();
        Integer diceValue = getDiceValue(gameId);
        Integer maxProgress = getPawnMaxProgress(pawn);
        if (pawn.getProgress() + diceValue > maxProgress) {
            return 2 * (maxProgress) - 2 * pawn.getProgress() - diceValue;
        }
        return diceValue;
    }

    private Integer getPawnFieldId(Pawn pawn) {
        ColorEnum pawnColor = getPawnColor(pawn);
        BoardEnum board = getGameBoardByGameId(pawn.getGameId());
        return boardService.getFieldIdForPawn(board, pawn, pawnColor);
    }

    private Integer getPawnMaxProgress(Pawn pawn) {
        Integer maxProgress = getBoardMaxProgress(pawn.getGameId());
        while (pawnConflictAtProgress(pawn.getGamePlayerId(), maxProgress)) {
            maxProgress--;
        }
        return maxProgress;
    }

    public boolean checkIfPlayerWon(Integer gameId, Integer playerId) {
        return pawnRepository.findByGameIdAndPlayerId(gameId, playerId)
                .stream()
                .allMatch(this::didFinish);
    }

    private void setWinner(Integer gameId, Integer playerId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        game.setWinnerPlayerId(playerId);
        gameRepository.save(game);
    }

    public Optional<Player> findWinner(Integer gameId) {
        return gameRepository.findById(gameId)
                .map(Game::getWinnerPlayerId)
                .flatMap(playerService::findPlayerById);
    }

    private boolean didFinish(Pawn pawn) {
        return getFinishedProgressRange(pawn)
                .anyMatch(pawn.getProgress()::equals);
    }

    private IntStream getFinishedProgressRange(Pawn pawn) {
        return IntStream.range(getPawnMaxProgress(pawn) + 1, getBoardMaxProgress(pawn.getGameId()) + 1);
    }

    private Integer getBoardMaxProgress(Integer gameId) {
        return boardService.getPathLength(getGameBoardByGameId(gameId)) - 1;
    }

    private boolean pawnConflictAtProgress(GamePlayerId gamePlayerId, Integer progress) {
        return pawnRepository.findByGameIdAndPlayerId(gamePlayerId.getGameId(), gamePlayerId.getPlayerId())
                .stream()
                .map(Pawn::getProgress)
                .anyMatch(progress::equals);

    }

    private Integer getGamePlayerStartFieldId(GamePlayerId gamePlayerId) {
        BoardEnum board = getGameBoardByGameId(gamePlayerId.getGameId());
        ColorEnum colorEnum = getGamePlayerColour(gamePlayerId);
        return boardService.getFieldId(board, NO_PROGRESS, colorEnum);
    }

    public Integer rollDice(Integer gameId) {
        Integer diceValue = new Random().nextInt(DICE_MAX) + 1;
        Game game = gameRepository.findById(gameId).orElseThrow();
        game.setCurrentDiceValue(diceValue);
        game.setDiceThrownInTurn(true);
        gameRepository.save(game);
        return diceValue;
    }

    public void nextTurn(Integer gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow();
        Integer nextPlayerId = findGamePlayersByGame(game).stream()
                .filter(gamePlayer -> gamePlayer.getGameId().equals(gameId))
                .filter(gamePlayer -> gamePlayer.getPlayerId().equals(game.getTurnPlayerId()))
                .map(GamePlayer::getNextPlayerId)
                .findFirst()
                .orElseThrow();
        game.setTurnPlayerId(nextPlayerId);
        game.setDiceThrownInTurn(false);
        updateGame(game);
    }

    public List<BoardMove> makeMoveWithPawn(PawnInfo pawnInfo, String nickname, Integer gameId) {
        Integer playerId = playerService.findPlayerByNickname(nickname)
                .map(Player::getId)
                .orElseThrow();
        Pawn selectedPawn = pawnRepository.findByGameIdAndPlayerId(gameId, playerId)
                .stream()
                .filter(pawn -> pawn.getNumber().equals(pawnInfo.getNumber()))
                .findFirst()
                .orElseThrow();
        selectedPawn.move(getMoveProgressDifference(selectedPawn));

        Integer destinationFieldId = getPawnFieldId(selectedPawn);
        List<BoardMove> boardMoves = new LinkedList<>();
        findPawnByGameIdAndFieldId(gameId, destinationFieldId).ifPresent(pawn -> {
            resetPawn(pawn);
            boardMoves.add(
                    BoardMove.builder()
                            .fromFieldId(destinationFieldId)
                            .toFieldId(getPawnFieldId(pawn))
                            .build()
            );
        });
        pawnRepository.save(selectedPawn);

        if (checkIfPlayerWon(gameId, playerId)) {
            setWinner(gameId, playerId);
        }

        boardMoves.add(
                BoardMove.builder()
                        .fromFieldId(pawnInfo.getFieldId())
                        .toFieldId(destinationFieldId)
                        .build()
        );

        return boardMoves;
    }

    private Optional<Pawn> findPawnByGameIdAndFieldId(Integer gameId, Integer fieldId) {
        Map<Integer, Integer> playerIdToProgress = boardService.getColorProgressMap(getGameBoardByGameId(gameId), fieldId)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(pair -> getPlayerIdByColor(gameId, pair.getKey()),
                        Map.Entry::getValue));

        return playerIdToProgress.entrySet()
                .stream()
                .map(playerProgress -> findPlayingPawnByPlayerAndProgress(gameId, playerProgress.getKey(), playerProgress.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();

    }

    private Optional<Pawn> findPlayingPawnByPlayerAndProgress(Integer gameId, Integer playerId, Integer progress) {
        return pawnRepository.findByGameIdAndPlayerId(gameId, playerId)
                .stream()
                .filter(Pawn::getDidStart)
                .filter(pawn -> pawn.getProgress().equals(progress))
                .findFirst();
    }

    private void resetPawn(Pawn pawn) {
        pawn.reset();
        pawnRepository.save(pawn);
    }

    private Integer getPlayerIdByColor(Integer gameId, ColorEnum color) {
        return gamePlayerRepository.findByGameId(gameId)
                .stream()
                .filter(gamePlayer -> gamePlayer.getPlayerColour().equals(color.getColor()))
                .map(GamePlayer::getPlayerId)
                .findFirst()
                .orElseThrow();
    }

    public Optional<Game> findById(Integer id) {
        return gameRepository.findById(id);
    }

    public List<Game> findByPlayerId(Integer playerId) {
        return gamePlayerRepository.findByPlayerId(playerId)
                .stream()
                .map(GamePlayer::getGameId)
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}

package model;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

/**
 * Immutable class representing the current game state.
 */
class GameState {

  final Board board;

  final Map<Player, Hand> playerHandMap;

  final Player currentPlayer;

  /**
   * Creates GameStates manually. It might be worth it to use {@link GameStateBuilder} instead.
   */
  GameState(Board board, Hand senteHand, Hand goteHand, Player currentPlayer) {
    this.board = board;

    this.playerHandMap = HashMap.of(
        Player.sente, senteHand,
        Player.gote, goteHand
    );
    this.currentPlayer = currentPlayer;
  }

  /**
   * Creates GameStates manually. It might be worth it to use {@link GameStateBuilder} instead.
   */
  GameState(Board board, Map<Player, Hand> playerHandMap, Player currentPlayer) {
    this.board = board;
    this.playerHandMap = playerHandMap;
    this.currentPlayer = currentPlayer;
  }

  /**
   * Tries to have the specified player move a piece from one position to another, possibly
   * promoting. Returns Option.none if could not perform the action, and Option.some(GameState) if
   * the action succeeded, and contains the next GameState.
   */
  Option<GameState> movePiece(Player player, Position fromPos, Position toPos,
      boolean promotes) {
    if (!moveIsValid(player, fromPos, toPos, promotes)) {
      return Option.none();
    }

    return Option.some(movePieceAndGetNewGameState(fromPos, toPos, promotes));
  }

  private boolean moveIsValid(Player player, Position fromPos, Position toPos,
      boolean promotes) {
    return pieceExists(fromPos)
        && isCurrentTurn(player)
        && ownsPiece(player, fromPos)
        && pieceHasAbilityToMoveToPosition(fromPos, toPos)
        && promotionIsLegal(player, fromPos, toPos, promotes);
  }

  private GameState movePieceAndGetNewGameState(Position fromPos, Position toPos,
      boolean promotes) {
    Player nextPlayer = getNextPlayer();
    Map<Player, Hand> newHand = moveCapturedPieceToHand(toPos);
    Board newBoard = getBoardAfterMovingPiece(fromPos, toPos, promotes);
    return new GameState(newBoard, newHand, nextPlayer);
  }

  private boolean pieceExists(Position fromPos) {
    return board.getPiece(fromPos).isDefined();
  }

  private boolean isCurrentTurn(Player player) {
    return player.equals(currentPlayer);
  }

  private boolean ownsPiece(Player player, Position fromPos) {
    return board.getPiece(fromPos).get().ownedBy.equals(player);
  }

  private boolean pieceHasAbilityToMoveToPosition(Position fromPos, Position toPos) {
    return board.getPiece(fromPos).get()
        .validPlacesToMove(currentPlayer, board, fromPos)
        .contains(toPos);
  }

  private boolean promotionIsLegal(Player player, Position fromPos, Position toPos,
      boolean promotes) {

    // if it has to promote and does, then it must be legal
    if (board.getPiece(fromPos).get().promotionIsForced(player, toPos)) {
      return promotes;
    }

    if (promotes) {
      if (player.equals(Player.sente)) {
        return fromPos.rank <= 3 || toPos.rank <= 3;
      } else {
        return fromPos.rank >= 7 || toPos.rank >= 7;
      }
    } else {
      return true;
    }
  }

  private Player getNextPlayer() {
    if (currentPlayer == Player.sente) {
      return Player.gote;
    } else {
      return Player.sente;
    }
  }

  private Map<Player, Hand> moveCapturedPieceToHand(Position toPos) {
    Option<Piece> pieceToCapture = board.getPiece(toPos);

    if (pieceToCapture.isDefined()) {
      return playerHandMap.put(currentPlayer,
          playerHandMap.get(currentPlayer).get().addPiece(pieceToCapture.get().getCopy(false)));
    } else {
      return playerHandMap.takeWhile(a -> true); // duplicate the hand
    }


  }

  private Board getBoardAfterMovingPiece(Position fromPos, Position toPos, boolean promotes) {
    Piece newPiece = getCopyOfPieceAndPossiblyPromote(fromPos, promotes);

    // TODO might be a weird issue with pieces being referenced from multiple boards
    // TODO transfer piece to hand if capture
    return board
        .setPiece(fromPos, null)
        .setPiece(toPos, newPiece);
  }

  private Piece getCopyOfPieceAndPossiblyPromote(Position fromPos, boolean promotes) {
    if (promotes) {
      return board.getPiece(fromPos).get().getCopy(true);
    } else {
      return board.getPiece(fromPos).get().getCopy();
    }
  }

  @Override
  public String toString() {
    return String.format(
        "%s\n\n%s\n\n%s",
        playerHandMap.get(Player.gote).get(),
        board.toString(),
        playerHandMap.get(Player.sente).get());
  }
}

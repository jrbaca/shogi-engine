package model;

import io.vavr.control.Option;

/**
 * Immutable class representing the current game state.
 */
class GameState {

  private final Board board;

  private final Hand senteHand;

  private final Hand goteHand;

  final Player currentPlayer;

  /**
   * Creates GameStates manually. It might be worth it to use {@link GameStateBuilder} instead.
   */
  GameState(Board board, Hand senteHand, Hand goteHand, Player currentPlayer) {
    this.board = board;
    this.senteHand = senteHand;
    this.goteHand = goteHand;
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
    return Option.some(movePieceAndGetNewGameState(fromPos, toPos));
  }

  private boolean moveIsValid(Player player, Position fromPos, Position toPos,
      boolean promotes) {
    return pieceExists(fromPos)
        && isCurrentTurn(player)
        && ownsPiece(player, fromPos)
        && pieceHasAbilityToMoveToPosition(fromPos, toPos);
  }

  private GameState movePieceAndGetNewGameState(Position fromPos, Position toPos) {
    Player nextPlayer = getNextPlayer();
    Board newBoard = getBoardAfterMovingPiece(fromPos, toPos);
    return new GameState(newBoard, senteHand, goteHand, nextPlayer);
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

  private Player getNextPlayer() {
    if (currentPlayer == Player.sente) {
      return Player.gote;
    } else {
      return Player.sente;
    }
  }

  private Board getBoardAfterMovingPiece(Position fromPos, Position toPos) {
    Piece pieceToMove = board.getPiece(fromPos).get();
    // TODO transfer piece to hand if capture
    // TODO this might cause some issues with board history
    return board
        .setPiece(fromPos, null)
        .setPiece(toPos, pieceToMove);
  }

  @Override
  public String toString() {
    return board.toString();
  }
}

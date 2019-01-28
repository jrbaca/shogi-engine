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
   * Don't use this for creating GameStates. Use {@link GameStateBuilder} instead.
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

    // Ensure piece exists
    Option<Piece> optPieceToMove = board.getPiece(fromPos);
    Piece pieceToMove;
    if (optPieceToMove.isEmpty()) {
      return Option.none();
    } else {
      pieceToMove = optPieceToMove.get();
    }

    // Exit if player tries to move out of turn or doesn't own that piece
    if (player != currentPlayer || player != pieceToMove.ownedBy) {
      return Option.none();
    }

    // Exit if invalid move
    if (!pieceToMove.validPlacesToMove(player, board, fromPos).contains(toPos)) {
      return Option.none();
    }

    // Set next player
    Player nextPlayer;
    if (currentPlayer == Player.sente) {
      nextPlayer = Player.gote;
    } else {
      nextPlayer = Player.sente;
    }

    // TODO transfer piece to hand if capture

    Board newBoard = board
        .setPiece(fromPos, null)
        .setPiece(toPos, pieceToMove); // TODO this might cause some issues with board history

    return Option.of(new GameState(newBoard, senteHand, goteHand, nextPlayer));
  }

  @Override
  public String toString() {
    return board.toString();
  }
}

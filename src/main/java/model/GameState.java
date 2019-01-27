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

  Option<GameState> movePiece(Player player, Position fromPos, Position toPos,
      boolean promotes) {

    // Exit if wrong player tries to move
    if (player != currentPlayer) {
      return Option.none();
    }

    // Set next player
    Player nextPlayer;
    if (currentPlayer == Player.sente) {
      nextPlayer = Player.gote;
    } else {
      nextPlayer = Player.sente;
    }

    // TODO check ownership
    Piece pieceToMove = board.getPiece(fromPos);
    return Option.of(new GameState(board.setPiece(fromPos, null)
        .setPiece(toPos, pieceToMove), senteHand, goteHand, nextPlayer));
  }

  @Override
  public String toString() {
    return board.toString();
  }

  enum Player {
    sente, gote
  }
}

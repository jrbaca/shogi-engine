package model;

import io.vavr.control.Option;

/**
 * Immutable class representing the current game state.
 */
class GameState {

  private final Board board;

  private final Hand senteHand;

  private final Hand goteHand;

  private final Player currentPlayer;

  private GameState(Board board, Hand senteHand, Hand goteHand, Player currentPlayer) {
    this.board = board;
    this.senteHand = senteHand;
    this.goteHand = goteHand;
    this.currentPlayer = currentPlayer;
  }

  /**
   * Returns a new {@link GameState} where all pieces are in their correct initial position.
   */
  static GameState setupNewGame() {
    Board board = new Board()
        .setPiece(Position.of(1, 1), new Lance())
        .setPiece(Position.of(2, 1), new Knight())
        .setPiece(Position.of(3, 1), new Silver())
        .setPiece(Position.of(4, 1), new Gold())
        .setPiece(Position.of(5, 1), new King(true))
        .setPiece(Position.of(6, 1), new Gold())
        .setPiece(Position.of(7, 1), new Silver())
        .setPiece(Position.of(8, 1), new Knight())
        .setPiece(Position.of(9, 1), new Lance())
        .setPiece(Position.of(1, 9), new Lance())
        .setPiece(Position.of(2, 9), new Knight())
        .setPiece(Position.of(3, 9), new Silver())
        .setPiece(Position.of(4, 9), new Gold())
        .setPiece(Position.of(5, 9), new King(false))
        .setPiece(Position.of(6, 9), new Gold())
        .setPiece(Position.of(7, 9), new Silver())
        .setPiece(Position.of(8, 9), new Knight())
        .setPiece(Position.of(9, 9), new Lance())
        .setPiece(Position.of(2, 2), new Bishop())
        .setPiece(Position.of(2, 8), new Rook())
        .setPiece(Position.of(8, 2), new Rook())
        .setPiece(Position.of(8, 8), new Bishop());

    for (int i = 1; i <= 9; i++) {
      board = board.setPiece(Position.of(i, 3), new Pawn());
      board = board.setPiece(Position.of(i, 7), new Pawn());
    }

    return new GameState(board, new Hand(), new Hand(), Player.sente);
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

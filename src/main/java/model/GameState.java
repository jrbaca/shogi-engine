package model;

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

  static GameState setupNewGame() {
    Board board = new Board()
        .setPiece(1, 1, new Lance())
        .setPiece(2, 1, new Knight())
        .setPiece(3, 1, new Silver())
        .setPiece(4, 1, new Gold())
        .setPiece(5, 1, new King(true))
        .setPiece(6, 1, new Gold())
        .setPiece(7, 1, new Silver())
        .setPiece(8, 1, new Knight())
        .setPiece(9, 1, new Lance())
        .setPiece(1, 9, new Lance())
        .setPiece(2, 9, new Knight())
        .setPiece(3, 9, new Silver())
        .setPiece(4, 9, new Gold())
        .setPiece(5, 9, new King(false))
        .setPiece(6, 9, new Gold())
        .setPiece(7, 9, new Silver())
        .setPiece(8, 9, new Knight())
        .setPiece(9, 9, new Lance())
        .setPiece(2, 2, new Bishop())
        .setPiece(2, 8, new Rook())
        .setPiece(8, 2, new Rook())
        .setPiece(8, 8, new Bishop());

    for (int i = 1; i <= 9; i++) {
      board = board.setPiece(i, 3, new Pawn());
      board = board.setPiece(i, 7, new Pawn());
    }

    return new GameState(board, new Hand(), new Hand(), Player.sente);
  }

  GameState movePiece(Player player, int fromFile, int fromRank, int toFile, int toRank,
      boolean promotes) {
    // TODO check ownership
    Piece pieceToMove = board.getPiece(fromFile, fromRank);
    return new GameState(board.setPiece(fromFile, fromRank, null)
        .setPiece(toFile, toRank, pieceToMove), senteHand, goteHand, currentPlayer);
  }

  @Override
  public String toString() {
    return board.toString();
  }

  enum Player {
    sente, gote
  }
}

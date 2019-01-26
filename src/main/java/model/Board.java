package model;

class Board {

  private final Piece[][] board;

  Board() {
    board = new Piece[9][9];
  }

  private Board(Piece[][] board) {
    this.board = board;
  }

  Board setPiece(int file, int rank, Piece piece) {
    Piece[][] newBoard = getBoardCopy();
    newBoard[file - 1][rank - 1] = piece;
    return new Board(newBoard);
  }

  Piece getPiece(int file, int rank) {
    return board[file - 1][rank - 1];
  }

  /**
   * Returns a new copy of the board for use in the next {@link GameState}.
   */
  private Piece[][] getBoardCopy() {
    Piece[][] newBoard = new Piece[board.length][];
    for (int i = 0; i < board.length; i++) {
      Piece[] currentRank = board[i];
      int rankLength = currentRank.length;
      newBoard[i] = new Piece[rankLength];
      System.arraycopy(currentRank, 0, newBoard[i], 0, rankLength);
    }
    return newBoard;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < 9; i++) {
      for (int j = 8; j >= 0; j--) {
        Piece currentPiece = board[j][i];
        if (currentPiece == null) {
          sb.append("[・]");
        } else {
          sb.append("[");
          sb.append(currentPiece.toString());
          sb.append("]");
        }
      }
      sb.append("\n");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }
}

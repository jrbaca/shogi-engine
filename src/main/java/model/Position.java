package model;

/**
 * A position on a shogi board.
 */
class Position {

  final int file;
  final int rank;

  private Position(int file, int rank) {
    this.file = file;
    this.rank = rank;
  }

  static Position of(int file, int rank) {
    return new Position(file, rank);
  }
}

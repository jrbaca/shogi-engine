package model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Position position = (Position) o;
    return file == position.file
        && rank == position.rank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(file, rank);
  }
}

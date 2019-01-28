package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class RangeMovement extends Movement {

  private int fileDirection;
  private int rankDirection;

  /**
   * Defines a ranging movement that goes in the specified direction. Eg rank=-1, file=0 goes
   * forward Eg rank=0, file=1 goes left Eg rank=1, file=1 goes down-left diagonal
   *
   * @param rankDirection 1 = backward, 0 = stationary, and -1 = forward
   * @param fileDirection 1 = left, 0 = stationary, and -1 = right
   */
  RangeMovement(int rankDirection, int fileDirection) {
    this.rankDirection = rankDirection;
    this.fileDirection = fileDirection;
  }

  @Override
  Set<Position> getValidPlacesToMove(Player player, Board board, Position from) {

    Position nextPosition;

    if (player.equals(Player.sente)) {
      nextPosition = Position.of(from.file + fileDirection, from.rank + rankDirection);
    } else {
      nextPosition = Position.of(from.file - fileDirection, from.rank - rankDirection);
    }

    if (positionIsNotInBounds(nextPosition)) {
      return HashSet.of();
    }

    if (board.getPiece(nextPosition).isEmpty()) {
      return HashSet.of(nextPosition).union(getValidPlacesToMove(player, board, nextPosition));
    } else if (board.getPiece(nextPosition).get().ownedBy.equals(player)) {
      return HashSet.of();
    } else {
      return HashSet.of(nextPosition);
    }
  }
}

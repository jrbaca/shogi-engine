package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;

class RangeMovement extends MovementUtil implements Movement {

  /**
   * Defines a ranging movement that goes in the specified direction. Eg rank=-1, file=0 goes
   * forward Eg rank=0, file=1 goes left Eg rank=1, file=1 goes down-left diagonal
   *
   * @param fileDirection 1 = left, 0 = stationary, and -1 = right
   * @param rankDirection 1 = backward, 0 = stationary, and -1 = forward
   */
  RangeMovement(int fileDirection, int rankDirection) {
    super(fileDirection, rankDirection);
  }

  Set<Position> getValidMovementPositionsBasedOnConcreteImpl(Player player, Board board,
      Position toPos) {

    Option<Piece> pieceAtToPos = board.getPiece(toPos);

    if (pieceAtToPos.isEmpty()) {
      return HashSet.of(toPos).union(getValidMovementPositions(player, board, toPos));
    } else if (pieceAtToPos.get().ownedBy.equals(player)) {
      return HashSet.of();
    } else {
      return HashSet.of(toPos);
    }
  }
}

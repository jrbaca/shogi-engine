package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;

class StepMovement extends MovementUtil implements Movement {

  /**
   * Defines a step movement that goes in the specified direction from blacks perspective. Eg
   * rank=-1, file=0 goes forward Eg rank=0, file=1 goes left Eg rank=1, file=1 goes down-left
   * diagonal
   *
   * @param fileStep 1 = left, 0 = stationary, and -1 = right
   * @param rankStep 1 = backward, 0 = stationary, and -1 = forward
   */
  StepMovement(int fileStep, int rankStep) {
    super(fileStep, rankStep);
  }

  Set<Position> getValidMovementPositionsBasedOnConcreteImpl(Player player, Board board,
      Position fromPos,
      Position toPos) {

    Option<Piece> pieceAtToPos = board.getPiece(toPos);

    if (pieceAtToPos.isEmpty()
        || !pieceAtToPos.get().ownedBy.equals(player)) {
      return HashSet.of(toPos);
    } else {
      return HashSet.of();
    }
  }
}

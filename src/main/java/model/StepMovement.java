package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;

class StepMovement extends Movement {

  private int fileStep;
  private int rankStep;

  /**
   * Defines a step movement that goes in the specified direction from blacks perspective. Eg
   * rank=-1, file=0 goes forward Eg rank=0, file=1 goes left Eg rank=1, file=1 goes down-left
   * diagonal
   *
   * @param rankStep 1 = backward, 0 = stationary, and -1 = forward
   * @param fileStep 1 = left, 0 = stationary, and -1 = right
   */
  StepMovement(int rankStep, int fileStep) {
    this.rankStep = rankStep;
    this.fileStep = fileStep;
  }

  @Override
  Set<Position> getValidPlacesToMove(Player player, Board board, Position from) {

    Position positionToMoveTo;
    if (player.equals(Player.sente)) {
      positionToMoveTo = Position.of(from.file + fileStep, from.rank + rankStep);
    } else {
      positionToMoveTo = Position.of(from.file - fileStep, from.rank - rankStep);
    }

    if (positionIsNotInBounds(positionToMoveTo)) {
      return HashSet.of();
    }

    Option<Piece> pieceAtPositionToMoveTo = board.getPiece(positionToMoveTo);

    if (pieceAtPositionToMoveTo.isEmpty()
        || !pieceAtPositionToMoveTo.get().ownedBy.equals(player)) {
      return HashSet.of(positionToMoveTo);
    } else {
      return HashSet.of();
    }
  }
}

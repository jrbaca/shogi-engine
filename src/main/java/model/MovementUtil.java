package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

/**
 * Utility class that supplies functions that are commonly used in determining movement.
 */
abstract class MovementUtil {

  private final int fileStep;
  private final int rankStep;

  MovementUtil(int fileStep, int rankStep) {
    this.fileStep = fileStep;
    this.rankStep = rankStep;
  }

  /**
   * Gets the {@link Position}s that a piece is capable of moving to.
   */
  @SuppressWarnings("WeakerAccess")
  public Set<Position> getValidMovementPositions(Player player, Board board, Position fromPos) {

    Position positionToMoveTo = transformMovementBasedOnPlayer(player, fromPos);

    if (positionIsNotInBounds(positionToMoveTo)) {
      return HashSet.of();
    }

    return getValidMovementPositionsBasedOnConcreteImpl(player, board, fromPos, positionToMoveTo);
  }

  private Position transformMovementBasedOnPlayer(Player player, Position fromPos) {
    if (player.equals(Player.sente)) {
      return Position.of(fromPos.file + fileStep, fromPos.rank + rankStep);
    } else {
      return Position.of(fromPos.file - fileStep, fromPos.rank - rankStep);
    }
  }

  private boolean positionIsNotInBounds(Position position) {
    return position.rank > 9
        || position.file > 9
        || position.rank < 1
        || position.file < 1;
  }

  abstract Set<Position> getValidMovementPositionsBasedOnConcreteImpl(Player player, Board board,
      Position fromPos,
      Position toPos);

}

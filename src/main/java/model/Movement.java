package model;

import io.vavr.collection.Set;

interface Movement {

  /**
   * Gets the {@link Position}s that a piece is capable of moving to.
   */
  Set<Position> getValidMovementPositions(Player player, Board board, Position fromPos);

}

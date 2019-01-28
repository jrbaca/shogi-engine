package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

/**
 * A movement that combines multiple simple movements such as {@link StepMovement} and {@link
 * RangeMovement}.
 */
class CompositeMovement implements Movement {

  private Set<Movement> children;

  private CompositeMovement() {
    children = HashSet.of();
  }

  /**
   * Creates a {@link CompositeMovement} from the given {@link Movement}s.
   */
  static CompositeMovement from(Set<Movement> movements) {
    CompositeMovement compositeMovement = new CompositeMovement();
    compositeMovement.children = movements;
    return compositeMovement;
  }

  @Override
  public Set<Position> getValidMovementPositions(Player player, Board board, Position fromPos) {
    return children.flatMap(child -> child.getValidMovementPositions(player, board, fromPos));
  }
}

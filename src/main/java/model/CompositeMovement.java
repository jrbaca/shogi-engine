package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class CompositeMovement extends Movement {

  private Set<Movement> children;

  private CompositeMovement() {
    children = HashSet.of();
  }

  static CompositeMovement from(Set<Movement> movements) {
    CompositeMovement compositeMovement = new CompositeMovement();
    compositeMovement.children = movements;
    return compositeMovement;
  }

  void addChild(Movement movement) {
    children = children.add(movement);
  }

  @Override
  Set<Position> getValidPlacesToMove(Player player, Board board, Position from) {
    return children.flatMap(child -> child.getValidPlacesToMove(player, board, from));
  }
}

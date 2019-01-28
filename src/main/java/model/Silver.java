package model;

import io.vavr.collection.HashSet;

class Silver extends Piece {

  private static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1),
          new StepMovement(1, -1),
          new StepMovement(-1, -1),
          new StepMovement(1, 1),
          new StepMovement(-1, 1)
      ));

  Silver(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "銀";
  }
}

package model;

import io.vavr.collection.HashSet;

class Gold extends Piece {

  private static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1),
          new StepMovement(1, -1),
          new StepMovement(-1, -1),
          new StepMovement(1, 0),
          new StepMovement(-1, 0),
          new StepMovement(0, 1)
      ));

  Gold(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "金";
  }
}

package model;

import io.vavr.collection.HashSet;

class Knight extends Piece {

  private static Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(-2, 1),
          new StepMovement(-2, -1)
      ));

  Knight(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "桂";
  }
}

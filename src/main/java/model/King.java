package model;

import io.vavr.collection.HashSet;

class King extends Piece {

  private static Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1),
          new StepMovement(0, 1),
          new StepMovement(-1, 0),
          new StepMovement(1, 0),
          new StepMovement(-1, -1),
          new StepMovement(1, -1),
          new StepMovement(-1, 1),
          new StepMovement(1, 1)
      ));

  King(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    if (ownedBy.equals(Player.gote)) {
      return "王";
    } else {
      return "玉";
    }
  }
}

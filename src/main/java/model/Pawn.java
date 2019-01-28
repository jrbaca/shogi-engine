package model;

import io.vavr.collection.HashSet;

class Pawn extends Piece {

  private static Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(-1, 0)
      ));

  Pawn(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "歩";
  }
}

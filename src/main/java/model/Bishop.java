package model;

import io.vavr.collection.HashSet;

public class Bishop extends Piece {

  private static Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(-1, -1),
          new RangeMovement(1, 1),
          new RangeMovement(-1, 1),
          new RangeMovement(1, -1)
      ));

  Bishop(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "角";
  }
}

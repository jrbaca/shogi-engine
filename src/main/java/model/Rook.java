package model;

import io.vavr.collection.HashSet;

class Rook extends Piece {

  private static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(0, -1),
          new RangeMovement(0, 1),
          new RangeMovement(-1, 0),
          new RangeMovement(1, 0)
      ));

  Rook(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "飛";
  }
}

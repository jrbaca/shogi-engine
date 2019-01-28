package model;

import io.vavr.collection.HashSet;

class Lance extends Piece {

  private static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(0, -1)
      ));

  Lance(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  public String toString() {
    return "香";
  }
}

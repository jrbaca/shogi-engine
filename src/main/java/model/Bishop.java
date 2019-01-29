package model;

import io.vavr.collection.HashSet;

class Bishop extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(-1, -1),
          new RangeMovement(1, 1),
          new RangeMovement(1, -1),
          new RangeMovement(-1, 1)
      ));

  private static final Movement promotedMovement = CompositeMovement.from(
      HashSet.of(
          movement,
          King.movement
      )
  );

  Bishop(Player ownedBy, boolean promoted) {
    super(ownedBy, promoted);
  }

  @Override
  Movement getPieceMovement() {
    if (promoted) {
      return promotedMovement;
    } else {
      return movement;
    }
  }

  @Override
  boolean promotionIsForced(Player player, Position toPos) {
    return false;
  }

  @Override
  Piece getCopy() {
    return new Bishop(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Bishop(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Bishop(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "馬";
    } else {
      return "角";
    }
  }
}

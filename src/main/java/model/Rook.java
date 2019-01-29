package model;

import io.vavr.collection.HashSet;

class Rook extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(0, -1),
          new RangeMovement(0, 1),
          new RangeMovement(-1, 0),
          new RangeMovement(1, 0)
      ));

  private static final Movement promotedMovement = CompositeMovement.from(
      HashSet.of(
          movement,
          King.movement
      )
  );

  Rook(Player ownedBy, boolean promoted) {
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
    return new Rook(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Rook(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Rook(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "龍";
    } else {
      return "飛";
    }
  }
}

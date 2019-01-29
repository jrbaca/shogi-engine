package model;

import io.vavr.collection.HashSet;

class Silver extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1),
          new StepMovement(1, -1),
          new StepMovement(-1, -1),
          new StepMovement(1, 1),
          new StepMovement(-1, 1)
      ));

  private static final Movement promotedMovement = Gold.movement;

  Silver(Player ownedBy, boolean promoted) {
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
    return new Silver(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Silver(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Silver(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "全";
    } else {
      return "銀";
    }
  }
}

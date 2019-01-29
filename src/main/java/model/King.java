package model;

import io.vavr.collection.HashSet;

class King extends Piece {

  static final Movement movement = CompositeMovement.from(
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

  King(Player ownedBy, boolean promoted) {
    super(ownedBy, promoted);
  }

  @Override
  Movement getPieceMovement() {
    return movement;
  }

  @Override
  boolean promotionIsForced(Player player, Position toPos) {
    return false;
  }

  @Override
  Piece getCopy() {
    return new King(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new King(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new King(owner, promoted);
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

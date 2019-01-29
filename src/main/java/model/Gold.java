package model;

import io.vavr.collection.HashSet;

class Gold extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1),
          new StepMovement(1, -1),
          new StepMovement(-1, -1),
          new StepMovement(1, 0),
          new StepMovement(-1, 0),
          new StepMovement(0, 1)
      ));

  Gold(Player ownedBy, boolean promoted) {
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
    return new Gold(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Gold(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Gold(owner, promoted);
  }

  @Override
  public String toString() {
    return "金";
  }
}

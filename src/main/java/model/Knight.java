package model;

import io.vavr.collection.HashSet;

class Knight extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(1, -2),
          new StepMovement(-1, -2)
      ));

  private static final Movement promotedMovement = Gold.movement;

  Knight(Player ownedBy, boolean promoted) {
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
    if (promoted) {
      return false;
    }

    if (player.equals(Player.sente)) {
      return toPos.rank <= 2;
    } else {
      return toPos.rank >= 8;
    }
  }

  @Override
  Piece getCopy() {
    return new Knight(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Knight(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Knight(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "今";
    } else {
      return "桂";
    }
  }
}

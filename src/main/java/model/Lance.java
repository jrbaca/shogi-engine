package model;

import io.vavr.collection.HashSet;

class Lance extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new RangeMovement(0, -1)
      ));

  private static final Movement promotedMovement = Gold.movement;

  Lance(Player ownedBy, boolean promoted) {
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
  @SuppressWarnings("Duplicates")
  boolean promotionIsForced(Player player, Position toPos) {
    if (promoted) {
      return false;
    }

    if (player.equals(Player.sente)) {
      return toPos.rank == 1;
    } else {
      return toPos.rank == 9;
    }
  }

  @Override
  Piece getCopy() {
    return new Lance(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Lance(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Lance(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "仝";
    } else {
      return "香";
    }
  }
}

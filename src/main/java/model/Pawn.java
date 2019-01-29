package model;

import io.vavr.collection.HashSet;

class Pawn extends Piece {

  static final Movement movement = CompositeMovement.from(
      HashSet.of(
          new StepMovement(0, -1)
      ));

  private static final Movement promotedMovement = Gold.movement;

  Pawn(Player ownedBy, boolean promoted) {
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
    return new Pawn(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted) {
    return new Pawn(ownedBy, promoted);
  }

  @Override
  Piece getCopy(boolean promoted, Player owner) {
    return new Pawn(owner, promoted);
  }

  @Override
  public String toString() {
    if (promoted) {
      return "と";
    } else {
      return "歩";
    }
  }
}

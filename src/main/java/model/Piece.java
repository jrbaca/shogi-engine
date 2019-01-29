package model;

import io.vavr.collection.Set;

abstract class Piece {

  final Player ownedBy;

  final boolean promoted;

  Piece(Player ownedBy, boolean promoted) {
    this.ownedBy = ownedBy;
    this.promoted = promoted;
  }

  /**
   * Returns all the spaces that a piece can move to.
   */
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return getPieceMovement().getValidMovementPositions(player, board, from);
  }

  abstract Movement getPieceMovement();

  abstract boolean promotionIsForced(Player player, Position toPos);

  abstract Piece getCopy();

  abstract Piece getCopy(boolean promoted);

  abstract Piece getCopy(boolean promoted, Player owner);
}

package model;

import io.vavr.collection.Set;

abstract class Piece {

  final Player ownedBy;

  Piece(Player ownedBy) {
    this.ownedBy = ownedBy;
  }

  /**
   * Returns all the spaces that a piece can move to.
   */
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return getPieceMovement().getValidMovementPositions(player, board, from);
  }

  abstract Movement getPieceMovement();
}

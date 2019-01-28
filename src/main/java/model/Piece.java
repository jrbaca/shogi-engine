package model;

import io.vavr.collection.Set;

abstract class Piece {

  final Player ownedBy;

  protected Piece(Player ownedBy) {
    this.ownedBy = ownedBy;
  }

  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return getPieceMovement().getValidMovementPositions(player, board, from);
  }

  abstract Movement getPieceMovement();
}

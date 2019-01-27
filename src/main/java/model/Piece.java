package model;

import io.vavr.collection.Set;

abstract class Piece {

  final Player ownedBy;

  protected Piece(Player ownedBy) {
    this.ownedBy = ownedBy;
  }

  abstract Set<Position> validPlacesToMove(Position from, Position to);
}

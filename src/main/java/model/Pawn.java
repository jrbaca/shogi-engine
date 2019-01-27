package model;

import io.vavr.collection.Set;

class Pawn extends Piece {


  Pawn(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Position from, Position to) {
    return null;
  }

  @Override
  public String toString() {
    return "歩";
  }
}

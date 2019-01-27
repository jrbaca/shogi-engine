package model;

import io.vavr.collection.Set;

class Knight extends Piece {

  Knight(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Position from, Position to) {
    return null;
  }

  @Override
  public String toString() {
    return "桂";
  }
}

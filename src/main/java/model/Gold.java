package model;

import io.vavr.collection.Set;

class Gold extends Piece {

  Gold(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Position from, Position to) {
    return null;
  }

  @Override
  public String toString() {
    return "金";
  }
}

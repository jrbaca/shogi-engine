package model;

import io.vavr.collection.Set;

class King extends Piece {

  King(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Position from, Position to) {
    return null;
  }

  @Override
  public String toString() {
    if (ownedBy.equals(Player.gote)) {
      return "王";
    } else {
      return "玉";
    }
  }
}

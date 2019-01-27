package model;

import io.vavr.collection.Set;

public class Bishop extends Piece {

  Bishop(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Position from, Position to) {
    return null;
  }

  @Override
  public String toString() {
    return "角";
  }
}

package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class Silver extends Piece {

  Silver(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return HashSet.of();
  }

  @Override
  public String toString() {
    return "銀";
  }
}

package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

public class Bishop extends Piece {

  Bishop(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return HashSet.of();
  }

  @Override
  public String toString() {
    return "角";
  }
}

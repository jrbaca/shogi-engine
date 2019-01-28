package model;

import io.vavr.collection.Set;

abstract class Movement {

  abstract Set<Position> getValidPlacesToMove(Player player, Board board, Position from);

  boolean positionIsNotInBounds(Position position) {
    return position.rank > 9
        || position.file > 9
        || position.rank < 1
        || position.file < 1;
  }

}

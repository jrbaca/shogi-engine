package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class Lance extends Piece {

  Lance(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return nextForward(player, board, from);
  }

  private Set<Position> nextForward(Player player, Board board, Position from) {

    Position nextPosition;

    if (player.equals(Player.sente)) {
      nextPosition = Position.of(from.file, from.rank - 1);
    } else {
      nextPosition = Position.of(from.file, from.rank + 1);
    }

    if (nextPosition.rank > 9
        || nextPosition.file > 9
        || nextPosition.rank < 1
        || nextPosition.file < 1) {
      return HashSet.of();
    }

    if (board.getPiece(nextPosition).isEmpty()) {
      return HashSet.of(nextPosition).union(nextForward(player, board, nextPosition));
    } else if (board.getPiece(nextPosition).get().ownedBy.equals(player)) {
      return HashSet.of();
    } else {
      return HashSet.of(nextPosition);
    }

  }

  @Override
  public String toString() {
    return "香";
  }
}

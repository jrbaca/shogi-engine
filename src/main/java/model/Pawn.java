package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class Pawn extends Piece {


  Pawn(Player ownedBy) {
    super(ownedBy);
  }

  @Override
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    Set<Position> potentialPositions;
    if (player.equals(Player.sente)) {
      potentialPositions = HashSet.of(Position.of(from.file, from.rank - 1));
    } else {
      potentialPositions = HashSet.of(Position.of(from.file, from.rank + 1));
    }

    // filter out positions owned by player
    return potentialPositions.filter((position) -> {
      if (board.getPiece(position).isEmpty()) {
        return true;
      } else {
        return !board.getPiece(position).get().ownedBy.equals(player);
      }
    });
  }

  @Override
  public String toString() {
    return "歩";
  }
}

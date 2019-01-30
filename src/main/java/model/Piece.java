package model;

import io.vavr.collection.Set;
import io.vavr.control.Option;
import java.util.Objects;

abstract class Piece {

  final Player ownedBy;

  final boolean promoted;

  Piece(Player ownedBy, boolean promoted) {
    this.ownedBy = ownedBy;
    this.promoted = promoted;
  }

  /**
   * Returns all the spaces that a piece can move to.
   */
  Set<Position> validPlacesToMove(Player player, Board board, Position from) {
    return getPieceMovement().getValidMovementPositions(player, board, from);
  }

  static Option<Piece> getPieceFromString(String pieceName, Player owner) {
    switch (pieceName) {
      case "king":
        return Option.some(new King(owner, false));
      case "rook":
        return Option.some(new Rook(owner, false));
      case "bishop":
        return Option.some(new Bishop(owner, false));
      case "gold":
        return Option.some(new Gold(owner, false));
      case "silver":
        return Option.some(new Silver(owner, false));
      case "knight":
        return Option.some(new Knight(owner, false));
      case "lance":
        return Option.some(new Lance(owner, false));
      case "pawn":
        return Option.some(new Pawn(owner, false));

      case "prook":
        return Option.some(new Rook(owner, true));
      case "pbishop":
        return Option.some(new Bishop(owner, true));
      case "psilver":
        return Option.some(new Silver(owner, true));
      case "pknight":
        return Option.some(new Knight(owner, true));
      case "plance":
        return Option.some(new Lance(owner, true));
      case "ppawn":
        return Option.some(new Pawn(owner, true));

      default:
        return Option.none();
    }
  }

  abstract Movement getPieceMovement();

  abstract boolean promotionIsForced(Player player, Position toPos);

  abstract Piece getCopy();

  abstract Piece getCopy(boolean promoted);

  abstract Piece getCopy(boolean promoted, Player owner);

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Piece piece = (Piece) o;
    return promoted == piece.promoted
        && ownedBy == piece.ownedBy;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ownedBy, promoted);
  }
}

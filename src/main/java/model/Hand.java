package model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

class Hand {

  private Set<Piece> handContents;

  Hand(Set<Piece> handContents) {
    this.handContents = handContents;
  }

  static Hand makeNew() {
    return new Hand(HashSet.empty());
  }

  /**
   * Returns a copy with the added piece.
   */
  Hand addPiece(Piece piece) {
    return new Hand(handContents.add(piece));
  }

  Hand removePiece(Piece piece) {
    return new Hand(handContents.remove(piece));
  }

  int size() {
    return handContents.size();
  }

  Hand getCopy() {
    return new Hand(handContents);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("{ ");
    for (Piece piece : handContents) {
      sb.append(piece);
      sb.append(" ");
    }

    sb.append("}");
    return sb.toString();
  }
}

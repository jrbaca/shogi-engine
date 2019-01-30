package model;

import io.vavr.collection.List;

class Hand {

  private List<Piece> handContents;

  private Hand(List<Piece> handContents) {
    this.handContents = handContents;
  }

  static Hand makeNew() {
    return new Hand(List.empty());
  }

  /**
   * Returns a copy with the added piece.
   */
  Hand addPiece(Piece piece) {
    return new Hand(handContents.append(piece));
  }

  Hand removePiece(Piece piece) {
    return new Hand(handContents.remove(piece));
  }

  boolean contains(Piece piece) {
    return handContents
        .map(currentPiece -> currentPiece.equals(piece))
        .fold(false, (l, r) -> l || r);
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

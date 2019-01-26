package model;

class King extends Piece {

  private boolean trueKing;

  King(boolean trueKing) {
    this.trueKing = trueKing;
  }

  @Override
  public String toString() {
    if (trueKing) {
      return "王";
    } else {
      return "玉";
    }
  }
}

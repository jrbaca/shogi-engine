package model;

/**
 * Builder class for creating custom {@link GameState GameStates}. Can be used to specify
 * initial conditions for a {@link GameBuilder}.
 */
class GameStateBuilder {

  private Board board;

  private Hand senteHand;

  private Hand goteHand;

  private Player currentPlayer;

  private GameStateBuilder() {
  }

  static GameStateBuilder fromEmptyState() {
    GameStateBuilder gameStateBuilder = new GameStateBuilder();
    gameStateBuilder.board = new Board();
    gameStateBuilder.senteHand = Hand.makeNew();
    gameStateBuilder.goteHand = Hand.makeNew();
    gameStateBuilder.currentPlayer = Player.sente;

    return gameStateBuilder;
  }

  static GameStateBuilder fromStandardInitialState() {
    GameStateBuilder gameStateBuilder = GameStateBuilder.fromEmptyState();
    gameStateBuilder
        .setPiece(Position.of(1, 1), new Lance(Player.gote, false))
        .setPiece(Position.of(2, 1), new Knight(Player.gote, false))
        .setPiece(Position.of(3, 1), new Silver(Player.gote, false))
        .setPiece(Position.of(4, 1), new Gold(Player.gote, false))
        .setPiece(Position.of(5, 1), new King(Player.gote, false))
        .setPiece(Position.of(6, 1), new Gold(Player.gote, false))
        .setPiece(Position.of(7, 1), new Silver(Player.gote, false))
        .setPiece(Position.of(8, 1), new Knight(Player.gote, false))
        .setPiece(Position.of(9, 1), new Lance(Player.gote, false))
        .setPiece(Position.of(1, 9), new Lance(Player.sente, false))
        .setPiece(Position.of(2, 9), new Knight(Player.sente, false))
        .setPiece(Position.of(3, 9), new Silver(Player.sente, false))
        .setPiece(Position.of(4, 9), new Gold(Player.sente, false))
        .setPiece(Position.of(5, 9), new King(Player.sente, false))
        .setPiece(Position.of(6, 9), new Gold(Player.sente, false))
        .setPiece(Position.of(7, 9), new Silver(Player.sente, false))
        .setPiece(Position.of(8, 9), new Knight(Player.sente, false))
        .setPiece(Position.of(9, 9), new Lance(Player.sente, false))
        .setPiece(Position.of(2, 2), new Bishop(Player.gote, false))
        .setPiece(Position.of(2, 8), new Rook(Player.sente, false))
        .setPiece(Position.of(8, 2), new Rook(Player.gote, false))
        .setPiece(Position.of(8, 8), new Bishop(Player.sente, false));

    for (int i = 1; i <= 9; i++) {
      gameStateBuilder.setPiece(Position.of(i, 3), new Pawn(Player.gote, false));
      gameStateBuilder.setPiece(Position.of(i, 7), new Pawn(Player.sente, false));
    }

    return gameStateBuilder;
  }

  GameState build() {
    return new GameState(board, senteHand, goteHand, currentPlayer);
  }

  GameStateBuilder setPiece(Position position, Piece piece) {
    board = board.setPiece(position, piece);
    return this;
  }

  GameStateBuilder setCurrentPlayer(Player player) {
    currentPlayer = player;
    return this;
  }

}

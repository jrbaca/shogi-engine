package model;

import model.GameState.Player;

/**
 * Builder class for creating custom {@link GameState}.
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
    gameStateBuilder.senteHand = new Hand();
    gameStateBuilder.goteHand = new Hand();
    gameStateBuilder.currentPlayer = Player.sente;

    return gameStateBuilder;
  }

  static GameStateBuilder fromStandardInitialPositions() {
    GameStateBuilder gameStateBuilder = GameStateBuilder.fromEmptyState();
    gameStateBuilder
        .setPiece(Position.of(1, 1), new Lance())
        .setPiece(Position.of(2, 1), new Knight())
        .setPiece(Position.of(3, 1), new Silver())
        .setPiece(Position.of(4, 1), new Gold())
        .setPiece(Position.of(5, 1), new King(true))
        .setPiece(Position.of(6, 1), new Gold())
        .setPiece(Position.of(7, 1), new Silver())
        .setPiece(Position.of(8, 1), new Knight())
        .setPiece(Position.of(9, 1), new Lance())
        .setPiece(Position.of(1, 9), new Lance())
        .setPiece(Position.of(2, 9), new Knight())
        .setPiece(Position.of(3, 9), new Silver())
        .setPiece(Position.of(4, 9), new Gold())
        .setPiece(Position.of(5, 9), new King(false))
        .setPiece(Position.of(6, 9), new Gold())
        .setPiece(Position.of(7, 9), new Silver())
        .setPiece(Position.of(8, 9), new Knight())
        .setPiece(Position.of(9, 9), new Lance())
        .setPiece(Position.of(2, 2), new Bishop())
        .setPiece(Position.of(2, 8), new Rook())
        .setPiece(Position.of(8, 2), new Rook())
        .setPiece(Position.of(8, 8), new Bishop());

    for (int i = 1; i <= 9; i++) {
      gameStateBuilder.setPiece(Position.of(i, 3), new Pawn());
      gameStateBuilder.setPiece(Position.of(i, 7), new Pawn());
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

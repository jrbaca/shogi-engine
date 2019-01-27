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

  GameState build() {
    return new GameState(board, senteHand, goteHand, currentPlayer);
  }

  GameStateBuilder setPiece(Position position, Piece piece) {
    board = board.setPiece(position, piece);
    return this;
  }

}

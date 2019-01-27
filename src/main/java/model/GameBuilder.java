package model;

/**
 * Builder class for setting initial conditions and rules for a new {@link Game}.
 */
class GameBuilder {

  private GameState gameState;

  private GameBuilder() {
  }

  /**
   * Returns a {@link GameBuilder} from a known {@link GameState}.
   */
  static GameBuilder fromGameState(GameState gameState) {
    GameBuilder gameBuilder = new GameBuilder();
    gameBuilder.gameState = gameState;
    return gameBuilder;
  }

  /**
   * Returns a {@link GameBuilder} with an empty board.
   */
  static GameBuilder fromEmptyBoard() {
    GameBuilder gameBuilder = new GameBuilder();
    GameStateBuilder gameStateBuilder = GameStateBuilder.fromEmptyState();

    gameBuilder.gameState = gameStateBuilder.build();
    return gameBuilder;
  }

  /**
   * Returns a {@link GameBuilder} using standard rules and starting positions.
   */
  static GameBuilder fromStandardRules() {
    GameBuilder gameBuilder = new GameBuilder();
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

    gameBuilder.gameState = gameStateBuilder.build();

    return gameBuilder;
  }

  /**
   * Creates a game with the previously defined settings.
   */
  Game build() {
    Game game = new Game();
    game.addNewGameState(gameState);
    return game;
  }
}

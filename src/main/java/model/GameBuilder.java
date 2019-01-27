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
   * Returns a {@link GameBuilder} using standard rules and starting positions.
   */
  static GameBuilder fromStandardRules() {
    GameBuilder gameBuilder = new GameBuilder();

    gameBuilder.gameState =
        GameStateBuilder.fromStandardInitialPositions().build();

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

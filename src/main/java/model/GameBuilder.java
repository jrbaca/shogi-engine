package model;

/**
 * Builder class for setting initial conditions and rules for a new {@link Game}. You may
 * use {@link #fromStandardInitialPositions()} to create a game with the normal starting
 * positions or {@link #fromGameState(GameState gameState)} to create one with custom
 * initial positions. {@link GameStateBuilder} can be especially useful for creating custom
 * initial conditions.
 */
class GameBuilder {

  private GameState gameState;

  private GameBuilder() {
  }

  /**
   * Returns a {@link GameBuilder} using standard rules.
   */
  static GameBuilder fromStandardRules() {
    return new GameBuilder();
  }

  /**
   * Returns a {@link GameBuilder} with initial conditions the same as the specified
   * {@link GameState}.
   */
  GameBuilder fromGameState(GameState gameState) {
    this.gameState = gameState;
    return this;
  }

  /**
   * Returns a {@link GameBuilder} with pieces placed in the standard starting positions.
   */
  GameBuilder fromStandardInitialPositions() {
    this.gameState = GameStateBuilder.fromStandardInitialState().build();
    return this;
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

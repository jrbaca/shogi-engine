package model;

import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal representation of a Shogi game. Tracks game history and knows custom rules.
 */
class Game {

  /**
   * A list of all past game states, including present, stored in reverse order.
   */
  private final List<GameState> gameHistoryReversed;

  /**
   * Don't use this for creating games. Use {@link GameBuilder} instead.
   */
  Game() {
    gameHistoryReversed = new ArrayList<>();
  }

  /**
   * Moves a piece from a position to another position.
   *
   * @param player black or white
   * @param fromPos the position to move from
   * @param toPos the position to move to
   * @param promotes whether or not the piece promotes
   */
  void movePiece(Player player, Position fromPos, Position toPos, boolean promotes) {
    Option<GameState> newGameState =
        getCurrentGameState().movePiece(player, fromPos, toPos, promotes);

    if (newGameState.isDefined()) {
      addNewGameState(newGameState.get());
    }
  }

  String getStringRepresentation() {
    return getCurrentGameState().toString();
  }

  GameState getCurrentGameState() {
    return gameHistoryReversed.get(0);
  }

  void addNewGameState(GameState gameState) {
    gameHistoryReversed.add(0, gameState);
  }
}

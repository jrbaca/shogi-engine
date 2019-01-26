package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal representation of a Shogi game. Tracks game history.
 */
class Game {

  /**
   * A list of all past game states, including present, stored in reverse order.
   */
  private List<GameState> gameHistoryReversed;

  Game() {
    gameHistoryReversed = new ArrayList<>();
    gameHistoryReversed.add(GameState.setupNewGame());
  }

  String getStringRepresentation() {
    return gameHistoryReversed.get(0).getStringRepresentation();
  }
}

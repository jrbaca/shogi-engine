package model;

import java.util.ArrayList;
import java.util.List;
import model.GameState.Player;

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

  /**
   * Moves a piece from a position to another position.
   *
   * @param player black or white
   * @param fromFile the file to move from
   * @param fromRank the rank to move from
   * @param toFile the file to move to
   * @param toRank the rank to move to
   * @param promotes whether or not the piece promotes
   */
  void movePiece(Player player, int fromFile, int fromRank, int toFile, int toRank,
      boolean promotes) {
    addNewGameState(getCurrentGameState()
        .movePiece(player, fromFile, fromRank, toFile, toRank, promotes));
  }

  String getStringRepresentation() {
    return getCurrentGameState().toString();
  }

  private GameState getCurrentGameState() {
    return gameHistoryReversed.get(0);
  }

  private void addNewGameState(GameState gameState) {
    gameHistoryReversed.add(0, gameState);
  }
}

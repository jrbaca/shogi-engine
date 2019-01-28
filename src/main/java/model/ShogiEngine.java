package model;

import javafx.scene.control.TextArea;

/**
 * The Shogi engine. Contains the logic to process commands and manipulate game state. Dispatches
 * any processed commands to {@link Game}.
 */
public class ShogiEngine {

  /**
   * Internal game representation.
   */
  private final Game game =
      GameBuilder
          .fromStandardRules()
          .fromStandardInitialPositions()
          .build();

  /**
   * Adapter for connecting to the view.
   */
  private final Model2ViewAdapter model2ViewAdapter;

  public ShogiEngine(Model2ViewAdapter model2ViewAdapter) {
    this.model2ViewAdapter = model2ViewAdapter;
  }

  /**
   * Interprets a command and updates the game state. Should be in form white/black moves/drops 84
   * [52] [promotes]
   *
   * @param command to be processed by the engine
   * @return A String representation of the result
   */
  public String processCommand(String command) {
    return Input.processCommand(command).run(game);
  }

  /**
   * Requests the current game state to be redrawn.
   *
   * @param textArea the text area to write to.
   */
  public void updateBoard(TextArea textArea) {
    textArea.setText(game.getStringRepresentation());
  }
}

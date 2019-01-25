package model;

import javafx.scene.control.TextArea;

/**
 * The Shogi engine. Contains the logic to process commands and manipulate game state. Dispatches
 * any processed commands to {@link Game}.
 */
public class ShogiModel {

  /**
   * Internal game representation.
   */
  private Game game = new Game();

  /**
   * Adapter for connecting to the view.
   */
  private Model2ViewAdapter model2ViewAdapter;

  public ShogiModel(Model2ViewAdapter model2ViewAdapter) {
    this.model2ViewAdapter = model2ViewAdapter;
  }

  /**
   * Interprets a command and updates the game state.
   *
   * @param command to be processed by the engine
   * @return A String representation of the result
   */
  public String processCommand(String command) {
    return "test";
  }

  /**
   * Requests the current game state to be redrawn.
   *
   * @param textArea the text area to write to.
   */
  public void updateBoard(TextArea textArea) {
    textArea.appendText(game.getStringRepresentation());
  }

}

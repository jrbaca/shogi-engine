package model;

import javafx.scene.control.TextArea;
import model.GameState.Player;

/**
 * The Shogi engine. Contains the logic to process commands and manipulate game state. Dispatches
 * any processed commands to {@link Game}.
 */
public class ShogiEngine {

  /**
   * Internal game representation.
   */
  private Game game = new Game();

  /**
   * Adapter for connecting to the view.
   */
  private Model2ViewAdapter model2ViewAdapter;

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
    String[] args = command.split(" ");

    Player player;
    if (args[0].equals("white")) {
      player = Player.gote;
    } else if (args[0].equals("black")) {
      player = Player.sente;
    } else {
      return "Unrecognised command";
    }

    String verb = args[1];
    String pos1 = args[2];

    if (verb.equals("moves")) {
      String pos2 = args[3];
      boolean promotes = args.length == 5 && args[4].equals("promotes");
      game.movePiece(player, Integer.valueOf(pos1.substring(0, 1)),
          Integer.valueOf(pos1.substring(1, 2)), Integer.valueOf(pos2.substring(0, 1)),
          Integer.valueOf(pos2.substring(1, 2)),
          promotes);
      return "Moved piece";
    } else if (verb.equals("drops")) {
      //TODO this
      return "Gonna drop";
    } else {
      return "Unrecognised command";
    }
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

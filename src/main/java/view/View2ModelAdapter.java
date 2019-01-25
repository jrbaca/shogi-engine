package view;

import javafx.scene.control.TextArea;

public interface View2ModelAdapter {

  /**
   * Requests the game board to be redrawn.
   *
   * @param textArea to draw to
   */
  void updateBoard(TextArea textArea);

  /**
   * Requests the model to process and act on the passed command.
   *
   * @param command to be processed
   * @return result of the operation
   */
  String processCommand(String command);
}

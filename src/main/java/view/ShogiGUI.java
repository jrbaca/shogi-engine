package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Graphical interface for the Shogi engine. This controls the logic for the view and is initialized
 * by {@link controller.ShogiController}.
 */
public class ShogiGUI implements Initializable {

  /**
   * Adapter for interacting with the model.
   */
  private View2ModelAdapter view2ModelAdapter;

  /**
   * Field where the user enters commands.
   */
  @FXML
  private TextField txtCommand;

  /**
   * Area where commands and their outputs are displayed.
   */
  @FXML
  private TextArea txtCommandOutput;

  /**
   * Area where the current game state is displayed.
   */
  @FXML
  private TextArea txtBoard;

  public void setView2ModelAdapter(View2ModelAdapter view2ModelAdapter) {
    this.view2ModelAdapter = view2ModelAdapter;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  }

  public void finishSetup() {
    view2ModelAdapter.updateBoard(txtBoard);
  }

  /**
   * Logic for processing a command with the shogi engine.
   */
  @FXML
  void submitCommand() {
    txtCommandOutput.appendText("> " + txtCommand.getText() + "\n");
    txtCommandOutput.appendText(view2ModelAdapter.processCommand(txtCommand.getText()) + "\n");
    txtCommand.clear();
    view2ModelAdapter.updateBoard(txtBoard);
  }
}

package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ShogiGUI implements Initializable {

  private View2ModelAdapter view2ModelAdapter;

  @FXML
  private TextField txtCommand;

  @FXML
  private TextArea txtCommandOutput;

  public void setView2ModelAdapter(View2ModelAdapter view2ModelAdapter) {
    this.view2ModelAdapter = view2ModelAdapter;
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
  }

  @FXML
  void submitCommand() {
    txtCommandOutput.appendText(txtCommand.getText());
    txtCommand.clear();
  }
}

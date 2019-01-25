package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Model2ViewAdapter;
import model.ShogiModel;
import view.ShogiGUI;
import view.View2ModelAdapter;

/**
 * Main application controller the initializes the engine and an interface for it.
 */
public class ShogiController extends Application {

  /**
   * The graphical interface.
   */
  private ShogiGUI shogiGUI;

  /**
   * The internal game representation engine.
   */
  private ShogiModel shogiModel;

  /**
   * Launches the game.
   *
   * @param args to launch with.
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    // Load the scene
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/scene.fxml"));
    Scene scene = new Scene(loader.load());
    scene.getStylesheets().add(getClass().getResource("/view/styles.css").toExternalForm());

    // Set up the stage
    stage.setTitle("ShogiEngine");
    stage.setScene(scene);

    // Set up the view
    shogiGUI = loader.getController();
    shogiGUI.setView2ModelAdapter(new View2ModelAdapter() {

      @Override
      public void updateBoard(TextArea textArea) {
        shogiModel.updateBoard(textArea);
      }

      @Override
      public String processCommand(String command) {
        return shogiModel.processCommand(command);
      }
    });

    // Set up the model
    shogiModel = new ShogiModel(new Model2ViewAdapter() {

    });

    stage.show();
  }
}
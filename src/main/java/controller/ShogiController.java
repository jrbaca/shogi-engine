package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model2ViewAdapter;
import model.ShogiModel;
import view.ShogiGUI;
import view.View2ModelAdapter;

public class ShogiController extends Application {

  ShogiGUI shogiGUI;

  ShogiModel shogiModel;

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

    });

    // Set up the model
    shogiModel = new ShogiModel(new Model2ViewAdapter() {

    });

    stage.show();
  }
}
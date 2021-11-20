package ooga.view.bottomView;

import java.io.File;
import java.net.MalformedURLException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

public class BottomView {

  public static final String PLAY_IMAGE = "images/play.png";
  public static final String PAUSE_IMAGE = "images/pause.png";
  public static final String STEP_IMAGE = "images/step.png";
  public static final String SLOW_IMAGE = "images/turtle.png";
  public static final String FAST_IMAGE = "images/rabbit.png";

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private Button playPauseButton;

  public BottomView (Controller controller) {
    bottomView = new VBox();
    myController = controller;
    makeSimulationButtons();
    makeSettingsButtons();
    initiateBottomView();
  }

  private void makeSimulationButtons() {
    playPauseButton = makeButton("Pause", e -> togglePlayPause());
//    File foo = new File("images/play.png");
//    Image play = new Image();
//    try {
//      Image play = new Image(foo.toURL().toString());
//    } catch (MalformedURLException e) {
//      e.printStackTrace();
//    }
//    playPauseButton.setGraphic(new ImageView(PLAY_IMAGE));
    bottomView.getChildren().add(playPauseButton);
  }

  private void togglePlayPause() {
    myController.pauseOrResume();
    if (playPauseButton.getText().equals("Play")) playPauseButton.setText("Pause");
    else playPauseButton.setText("Play");
  }

  private void makeSettingsButtons() {
    HBox settings = new HBox();
    Button statsButton   = makeButton("Stats", e -> showStats());
    Button newGameButton = makeButton("New Game", e -> resetGame());
    settings.getChildren().addAll(statsButton, newGameButton);
    bottomView.getChildren().add(settings);
  }

  private GridPane initiateBottomView() {
    Button easyButton    = makeButton("Easy", e -> makeGameEasy());
    Button hardButton    = makeButton("Hard", e -> makeGameHard());
    Button statsButton   = makeButton("Stats", e -> showStats());
    Button newGameButton = makeButton("New Game", e -> resetGame());

    bottomGrid = new GridPane();
    bottomGrid.add(easyButton, 1, 1); // This might be in the config file
    bottomGrid.add(hardButton, 2, 1); // This might be in the config file
    bottomGrid.add(statsButton, 3, 1);
    bottomGrid.add(newGameButton, 4, 1);

    return bottomGrid;
  }

  private void makeGameEasy() {
    // TODO: Implement
  }

  private void makeGameHard() {
    // TODO: Implement
  }

  private void showStats() {
    // TODO: implement pauseGame function here
    Alert statsPopup = new Alert(Alert.AlertType.NONE);
    // TODO: Wire all text to resources files
    statsPopup.setTitle("GAME STATS");
    statsPopup.setContentText(
        "All time high score: " + "\n" +
            "Your lives: " + "\n" +
            "Other stats: " + "\n");
    statsPopup.showAndWait();
  }

  private void resetGame() {
    // TODO: implement resetGame function here
  }

  private Button makeButton(String name, EventHandler<ActionEvent> handler) {
    Button myButton = new Button(name);
    myButton.setOnAction(handler);
    return myButton;
  }

  public Node getBottomViewGP() {
    return bottomView;
//        return this.bottomGrid;
  }
}

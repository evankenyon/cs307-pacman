package ooga.view.bottomView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;

public class BottomView {

  public static final String PAUSE_IMAGE = "https://theologygaming.com/wp-content/uploads/2014/08/Pause.png";
  public static final String PLAY_IMAGE = "https://cdn-icons-png.flaticon.com/512/109/109197.png";
//  public static final String PLAY_IMAGE = "images/play.png";
//  public static final String PAUSE_IMAGE = "images/pause.png";
  public static final String STEP_IMAGE = "images/step.png";
  public static final String SLOW_IMAGE = "images/turtle.png";
  public static final String FAST_IMAGE = "images/rabbit.png";
  public static final int BUTTON_SIZE = 50;

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private Button playPauseButton;
  private boolean isPaused = false;
  private ImageView pauseButton;
  private ImageView playButton;

  public BottomView (Controller controller) {
    bottomView = new VBox();
    bottomView.setAlignment(Pos.TOP_CENTER);
    myController = controller;
    makeSimulationButtons();
    makeSettingsButtons();
    initiateBottomView();
  }

  private void makeSimulationButtons() {
    makeButtonImages();
    playPauseButton = makeButton("", e -> togglePlayPause());
    playPauseButton.setGraphic(pauseButton);
    playPauseButton.setBackground(Background.EMPTY);
    bottomView.getChildren().add(playPauseButton);
  }

  private void makeButtonImages() {
    pauseButton = new ImageView(PAUSE_IMAGE);
    pauseButton.setFitHeight(BUTTON_SIZE);
    pauseButton.setFitWidth(BUTTON_SIZE);
    playButton = new ImageView(PLAY_IMAGE);
    playButton.setFitHeight(BUTTON_SIZE);
    playButton.setFitWidth(BUTTON_SIZE);
  }

  private void togglePlayPause() {
    myController.pauseOrResume();
    if (isPaused) {
      playPauseButton.setGraphic(pauseButton);
      isPaused = false;
    }
    else {
      playPauseButton.setGraphic(playButton);
      isPaused = true;
    }
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

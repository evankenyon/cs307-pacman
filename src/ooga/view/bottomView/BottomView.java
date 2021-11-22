package ooga.view.bottomView;

import static ooga.controller.Controller.SECOND_DELAY;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;
import ooga.model.VanillaGame;

public class BottomView {

//  public static final String PAUSE_IMAGE = "https://theologygaming.com/wp-content/uploads/2014/08/Pause.png";
//  public static final String PLAY_IMAGE = "https://cdn-icons-png.flaticon.com/512/109/109197.png";
  public static final String PLAY_IMAGE = "data/images/play.png";
  public static final String PAUSE_IMAGE = "data/images/pause.png";
//  public static final String STEP_IMAGE = "https://toppng.com/uploads/preview/rotate-object-ui-arrow-round-svg-png-icon-free-download-arrow-icon-white-png-rotate-11563011162pfybbrrqls.png";
  public static final String STEP_IMAGE = "data/images/step.png";
  public static final String SLOW_IMAGE = "images/turtle.png";
  public static final String FAST_IMAGE = "images/rabbit.png";
  public static final int BUTTON_SIZE = 50;

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private VanillaGame myGame;
  private Button playPauseButton;
  private Button stepButton;
  private boolean isPaused = false;
  private ImageView pauseButtonImage;
  private ImageView playButtonImage;
  private ImageView stepButtonImage;

  public BottomView (Controller controller, VanillaGame game) {
    myController = controller;
    myGame = game;
    bottomView = new VBox();
    bottomView.setAlignment(Pos.TOP_CENTER);
    makeSimulationButtons();
    makeSettingsButtons();
    initiateBottomView();
  }

  private void makeSimulationButtons() {
    HBox simButtons = new HBox();
    simButtons.setAlignment(Pos.CENTER);
    makeButtonImages();
    playPauseButton = makeSimButton(pauseButtonImage, Background.EMPTY, e -> togglePlayPause());
    stepButton = makeSimButton(stepButtonImage, Background.EMPTY, e -> myGame.step());
    simButtons.getChildren().addAll(playPauseButton, stepButton);
    bottomView.getChildren().add(simButtons);
  }

  private Button makeSimButton(ImageView image, Background background, EventHandler<ActionEvent> handler) {
    Button myButton = new Button();
    myButton.setOnAction(handler);
    myButton.setGraphic(image);
    myButton.setBackground(background);
    return myButton;
  }

  private void makeButtonImages() {
    pauseButtonImage = new ImageView(new Image(new File(PAUSE_IMAGE).toURI().toString()));
    pauseButtonImage.setFitHeight(BUTTON_SIZE);
    pauseButtonImage.setFitWidth(BUTTON_SIZE);
    playButtonImage = new ImageView(new Image(new File(PLAY_IMAGE).toURI().toString()));
    playButtonImage.setFitHeight(BUTTON_SIZE);
    playButtonImage.setFitWidth(BUTTON_SIZE);
    stepButtonImage = new ImageView(new Image(new File(STEP_IMAGE).toURI().toString()));
    stepButtonImage.setFitWidth(BUTTON_SIZE);
    stepButtonImage.setFitHeight(BUTTON_SIZE);
  }

  private void togglePlayPause() {
    myController.pauseOrResume();
    if (isPaused) {
      playPauseButton.setGraphic(pauseButtonImage);
      isPaused = false;
    }
    else {
      playPauseButton.setGraphic(playButtonImage);
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

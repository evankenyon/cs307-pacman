package ooga.view.bottomView;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
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
  public static final String SLOW_IMAGE = "data/images/turtle.png";
  public static final String FAST_IMAGE = "data/images/rabbit.png";
  public static final int BUTTON_SIZE = 50;
  public static final int ICON_SIZE = 20;
  public static final double MIN_SLIDER_VAL = 0.5;
  public static final double MAX_SLIDER_VAL = 5;
  public static final double INITIAL_RATE = 1;
  public static final int SLIDER_LENGTH = 200;

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private VanillaGame myGame;
  private Button playPauseButton;
  private Button stepButton;
  private boolean isPaused = false;

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
    simButtons.setAlignment(Pos.BASELINE_CENTER);
    playPauseButton = makeSimButton(makeButtonImage(PAUSE_IMAGE, BUTTON_SIZE), Background.EMPTY, e -> togglePlayPause());
    stepButton = makeSimButton(makeButtonImage(STEP_IMAGE, BUTTON_SIZE), Background.EMPTY, e -> myGame.step());
    Node slider = makeSpeedSlider();
    simButtons.getChildren().addAll(playPauseButton, stepButton, slider);
    bottomView.getChildren().add(simButtons);
  }

  private Node makeSpeedSlider() {
    HBox sliderBox = new HBox();
    Slider speedSlider = new Slider(MIN_SLIDER_VAL, MAX_SLIDER_VAL, INITIAL_RATE);
    speedSlider.setPrefWidth(SLIDER_LENGTH);
    speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> myController.setAnimationSpeed(speedSlider.getValue()));
    speedSlider.setId("speedSlider");
    sliderBox.getChildren().add(makeButtonImage(SLOW_IMAGE, ICON_SIZE));
    sliderBox.getChildren().add(speedSlider);
    sliderBox.getChildren().add(makeButtonImage(FAST_IMAGE, ICON_SIZE));
    sliderBox.getStyleClass().add("speedSlider");
    return sliderBox;
  }

  private Button makeSimButton(ImageView image, Background background, EventHandler<ActionEvent> handler) {
    Button myButton = new Button();
    myButton.setOnAction(handler);
    myButton.setGraphic(image);
    myButton.setBackground(background);
    return myButton;
  }

  private ImageView makeButtonImage(String path, int size) {
    ImageView image = new ImageView(new Image(new File(path).toURI().toString()));
    image.setFitHeight(size);
    image.setFitWidth(size);
    return image;
  }

  private void togglePlayPause() {
    myController.pauseOrResume();
    if (isPaused) {
      playPauseButton.setGraphic(makeButtonImage(PAUSE_IMAGE, BUTTON_SIZE));
      isPaused = false;
    }
    else {
      playPauseButton.setGraphic(makeButtonImage(PLAY_IMAGE, BUTTON_SIZE));
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

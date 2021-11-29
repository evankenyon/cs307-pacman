package ooga.view.bottomView;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.model.VanillaGame;
import ooga.view.mainView.MainView;

public class BottomView {
  public static final String PLAY_IMAGE = "data/images/play.png";
  public static final String PAUSE_IMAGE = "data/images/pause.png";
  public static final String STEP_IMAGE = "data/images/step.png";
  public static final String SLOW_IMAGE = "data/images/turtle.png";
  public static final String FAST_IMAGE = "data/images/rabbit.png";
  public static final String BOTTOMVIEW_PACKAGE = "ooga.view.bottomView.";
  public static final String STYLESHEET = String.format("/%sBottomView.css",
      BOTTOMVIEW_PACKAGE.replace(".", "/"));
  public static final int BUTTON_SIZE = 50;
  public static final int ICON_SIZE = 20;
  public static final double MIN_SLIDER_VAL = 0.5;
  public static final double MAX_SLIDER_VAL = 5;
  public static final double INITIAL_RATE = 1;
  public static final int SLIDER_LENGTH = 200;

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private ResourceBundle myResources;
  private VanillaGame myGame;
  private Button playPauseButton;
  private Button stepButton;
  private String myLanguage;
  private boolean isPaused = false;

  public BottomView (Controller controller, VanillaGame game, String language) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    myController = controller;
    myGame = game;
    myLanguage = language;
    bottomView = new VBox();
    bottomView.getStyleClass().add("root");
    bottomView.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    initiateBottomView();
  }

  private HBox makeSpeedSlider() {
    HBox sliderBox = new HBox();
    sliderBox.getStyleClass().add("speedSlider");
    sliderBox.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    Slider speedSlider = new Slider(MIN_SLIDER_VAL, MAX_SLIDER_VAL, INITIAL_RATE);
    speedSlider.setPrefWidth(SLIDER_LENGTH);
    speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> myController.setAnimationSpeed(speedSlider.getValue()));
    speedSlider.setId("speedSlider");
    sliderBox.getChildren().add(makeButtonImage(SLOW_IMAGE, ICON_SIZE));
    sliderBox.getChildren().add(speedSlider);
    sliderBox.getChildren().add(makeButtonImage(FAST_IMAGE, ICON_SIZE));
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

  private void initiateBottomView() {
//    ImageView saveButton    = makeGraphicButton("save", e -> saveGame());
//    ImageView statsButton   = makeGraphicButton("stats", e -> showStats());
//    ImageView restartButton = makeGraphicButton("restart", e -> restartGame());

    playPauseButton = makeSimButton(makeButtonImage(PAUSE_IMAGE, BUTTON_SIZE), Background.EMPTY, e -> togglePlayPause());
    playPauseButton.setId("playPauseButton");
    stepButton = makeSimButton(makeButtonImage(STEP_IMAGE, BUTTON_SIZE), Background.EMPTY, e -> myGame.step());
    stepButton.setId("stepButton");
    HBox simButtons = new HBox();
    simButtons.getStyleClass().add("root");
    simButtons.getChildren().addAll(playPauseButton, stepButton);
    HBox slider = makeSpeedSlider();
    Button statsButton = makeSimpleButton("Stats", e -> showStats());
    Button restartButton = makeSimpleButton("Restart", e -> restartGame());
    HBox gameButtons = new HBox();
    gameButtons.getStyleClass().add("game-buttons");
    gameButtons.getChildren().addAll(statsButton, restartButton);
    bottomView.getChildren().addAll(simButtons, slider, gameButtons);
  }

  private Button makeSimpleButton(String name, EventHandler action) {
    Button statsButton = new Button(myResources.getString(name));
    statsButton.setOnAction(action);
    return statsButton;
  }

  private ImageView makeGraphicButton(String key, EventHandler handler) {
    String value = myResources.getString(key);
    String imagePath = "data/images/" + key + "Button-" + value + ".png";
    ImageView myImgView = new ImageView(new Image(new File(imagePath).toURI().toString()));
    myImgView.setPreserveRatio(true);
    myImgView.setFitWidth(150);
    myImgView.setOnMouseReleased(handler);
    return myImgView;
  }

  private void makeGameEasy() {
    // TODO: Implement
  }

  private void makeGameHard() {
    // TODO: Implement
  }

  private void showStats() {
    togglePlayPause();
    Alert statsPopup = new Alert(AlertType.INFORMATION);
    // TODO: Wire all text to resources files
    statsPopup.setHeaderText(myResources.getString("Stats"));
    statsPopup.setContentText(
        "All time high score: " + "\n" +
            "Your lives: " + "\n" +
            "Other stats: " + "\n");
    statsPopup.showAndWait();
    togglePlayPause();
  }

  private void restartGame() {
    // TODO: implement resetGame function here
    myController.restartGame();
  }

  public Node getBottomViewGP() {
    return bottomView;
//        return this.bottomGrid;
  }

}

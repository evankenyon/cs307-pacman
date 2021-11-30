package ooga.view.bottomView;

import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;

import java.io.File;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import ooga.model.VanillaGame;

/**
 * Class that creates a VBox to be placed in the bottom of the BorderPane in MainView. This class
 * creates simulation controls such as play/pause button, step button, animation speed, stats
 * button, and restart game button.
 *
 * @author Dane Erickson, Kat Cottrell
 */
public class BottomView {

  public static final String PLAY_IMAGE = "data/images/bigPlayIcon.png";
  public static final String PAUSE_IMAGE = "data/images/bigPauseIcon.png";
  public static final String STEP_IMAGE = "data/images/bigSkipIcon.png";
  public static final String SLOW_IMAGE = "data/images/turtle.png";
  public static final String FAST_IMAGE = "data/images/rabbit.png";
  public static final String BOTTOMVIEW_PACKAGE = "ooga.view.bottomView.";
  public static final String STYLESHEET = String.format("/%sBottomView.css",
      BOTTOMVIEW_PACKAGE.replace(".", "/"));
  public static final int SIM_BUTTON_SIZE = 75;
  public static final int ICON_SIZE = 20;
  public static final double MIN_SLIDER_VAL = 0.5;
  public static final double MAX_SLIDER_VAL = 5;
  public static final double INITIAL_RATE = 1;
  public static final int SLIDER_LENGTH = 200;
  private static final int GRAPHIC_BUTTON_HEIGHT = 25;

  private GridPane bottomGrid;
  private VBox bottomView;
  private Controller myController;
  private ResourceBundle myResources;
  private VanillaGame myGame;
  private Button playPauseButton;
  private Button stepButton;
  private String myLanguage;
  private boolean isPaused = false;

  /**
   * Constructor to create a BottomView, which makes simulation and game buttons on the bottom of
   * the screen.
   *
   * @param controller is the Controller used for this game
   * @param game       is the model object that runs the game
   * @param language   is a String for the language being used in the game
   */
  public BottomView(Controller controller, VanillaGame game, String language) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    myController = controller;
    myGame = game;
    myLanguage = language;
    bottomView = new VBox();
    bottomView.getStyleClass().add("root");
    bottomView.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    initiateBottomView(bottomView);
  }

  private HBox makeSpeedSlider() {
    HBox sliderBox = new HBox();
    sliderBox.getStyleClass().add("speedSlider");
    sliderBox.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    Slider speedSlider = new Slider(MIN_SLIDER_VAL, MAX_SLIDER_VAL, INITIAL_RATE);
    speedSlider.setPrefWidth(SLIDER_LENGTH);
    speedSlider.valueProperty().addListener(
        (observable, oldValue, newValue) -> myController.setAnimationSpeed(speedSlider.getValue()));
    speedSlider.setId("speedSlider");
    sliderBox.getChildren().add(makeButtonImage(SLOW_IMAGE, ICON_SIZE));
    sliderBox.getChildren().add(speedSlider);
    sliderBox.getChildren().add(makeButtonImage(FAST_IMAGE, ICON_SIZE));
    return sliderBox;
  }

  private Button makeSimButton(ImageView image, Background background,
      EventHandler<ActionEvent> handler) {
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
      playPauseButton.setGraphic(makeButtonImage(PAUSE_IMAGE, SIM_BUTTON_SIZE));
      isPaused = false;
    } else {
      playPauseButton.setGraphic(makeButtonImage(PLAY_IMAGE, SIM_BUTTON_SIZE));
      isPaused = true;
    }
  }

  private void initiateBottomView(VBox root) {
    ImageView saveButton = makeGraphicButton("save", e -> saveGame());
    ImageView statsButton = makeGraphicButton("stats", e -> showStats());
    ImageView restartButton = makeGraphicButton("restart", e -> restartGame());
    VBox graphicButtons = new VBox();
    graphicButtons.getStyleClass().add("graphic-buttons");
    graphicButtons.getChildren().addAll(saveButton, statsButton, restartButton);

    playPauseButton = makeSimButton(makeButtonImage(PAUSE_IMAGE, SIM_BUTTON_SIZE), Background.EMPTY,
        e -> togglePlayPause());
    playPauseButton.setId("playPauseButton");
    stepButton = makeSimButton(makeButtonImage(STEP_IMAGE, SIM_BUTTON_SIZE), Background.EMPTY,
        e -> myGame.step());
    stepButton.setId("stepButton");
    HBox buttonsPane = new HBox();
    buttonsPane.getStyleClass().add("root");
    buttonsPane.getChildren().addAll(graphicButtons, playPauseButton, stepButton);

    HBox slider = makeSpeedSlider();
    root.getChildren().addAll(buttonsPane, slider);
  }


  private Button makeSimpleButton(String name, EventHandler action) {
    Button statsButton = new Button(myResources.getString(name));
    statsButton.setOnAction(action);
    return statsButton;
  }

  private void saveGame() {
    // TODO: Fix SaveGame when merged
//    new SaveGame().saveGame();
  }

  private ImageView makeGraphicButton(String key, EventHandler handler) {
    String value = myResources.getString(key);
    String imagePath = "data/images/" + key + "Button-" + value + ".png";
    ImageView myImgView = new ImageView(new Image(new File(imagePath).toURI().toString()));
    myImgView.setPreserveRatio(true);
    myImgView.setFitHeight(GRAPHIC_BUTTON_HEIGHT);
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
    // TODO: Get the actual stats from the model
    statsPopup.setHeaderText(myResources.getString("Stats"));
    statsPopup.setContentText(String.format(myResources.getString("HighScore")
        .concat(myResources.getString("PelletsEaten"))
        .concat(myResources.getString("GhostsEaten")), 0,0,0));
    statsPopup.showAndWait();
    togglePlayPause();
  }

  private void restartGame() {
    // TODO: implement resetGame function here
    myController.restartGame();
  }

  /**
   * Getter method to return the VBox that holds all the buttons and other Nodes to be placed at the
   * bottom of the screen.
   *
   * @return bottomView is a Node with the items to be placed on the bottom of the screen.
   */
  public Node getBottomViewGP() {
    return bottomView;
//        return this.bottomGrid;
  }

}

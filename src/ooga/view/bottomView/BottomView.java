package ooga.view.bottomView;

import static ooga.view.center.agents.MovableView.IMAGE_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH;
import static ooga.view.startupView.GameStartupPanel.RESOURCES_PATH_WITH_LANGUAGE;
import static ooga.view.mainView.MainView.BG_COLOR;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.GameSaver;
import ooga.controller.IO.User;
import ooga.model.VanillaGame;
import ooga.view.popups.ErrorPopups;
import ooga.view.userProfileView.UserInformationView;

/**
 * Class that creates a VBox to be placed in the bottom of the BorderPane in MainView. This class
 * creates simulation controls such as play/pause button, step button, animation speed, stats
 * button, and restart game button.
 *
 * @author Dane Erickson, Kat Cottrell
 */
public class BottomView {

  public static final String PLAY_IMAGE = "bigPlayIcon.png";
  public static final String PAUSE_IMAGE = "bigPauseIcon.png";
  public static final String STEP_IMAGE = "bigSkipIcon.png";
  public static final String SLOW_IMAGE = "data/images/turtle-8bit.png";
  public static final String FAST_IMAGE = "data/images/rabbit-8bit.png";
  public static final String BOTTOMVIEW_PACKAGE = "ooga.view.bottomView.";
  public static final String STYLESHEET = String.format("/%sBottomView.css",
      BOTTOMVIEW_PACKAGE.replace(".", "/"));
  public static final String PLAY_PAUSE_BUTTON_ID = "playPauseButton";
  public static final String STEP_BUTTON_ID = "stepButton";
  public static final String SAVE_BUTTON_ID = "saveButton";
  public static final String STATS_BUTTON_ID = "statsButton";
  public static final String NEW_GAME_BUTTON_ID = "newGameButton";
  public static final int ICON_SIZE = 30;
  public static final int SIM_BUTTON_SIZE = 120;
  public static final double MIN_SLIDER_VAL = 0.5;
  public static final double MAX_SLIDER_VAL = 2;
  public static final double INITIAL_RATE = 1;
  public static final int SLIDER_LENGTH = 200;
  public static final int PFP_BORDER_WIDTH = 6;
  public static final int GRAPHIC_BUTTON_HEIGHT = 36;
  public static final int PADDING = 20;
  public static final int X_SPACING = 15;
  public static final int Y_SPACING = 6;

  private Stage myStage;
  private HBox bottomView;
  private Controller myController;
  private ResourceBundle myResources;
  private VanillaGame myGame;
  private String myLanguage;
  private boolean isPaused;
  private User myUser;
  private ImageView playPauseButton;

  /**
   * Constructor to create a BottomView, which makes simulation and game buttons on the bottom of
   * the screen.
   *
   * @param controller is the Controller used for this game
   * @param game       is the model object that runs the game
   * @param language   is a String for the language being used in the game
   */
  public BottomView(Controller controller, VanillaGame game, Stage stage, String language, User user) {
    myResources = ResourceBundle.getBundle(String.format("%s%s", RESOURCES_PATH, language));
    myController = controller;
    myGame = game;
    myStage = stage;
    myLanguage = language;
    myUser = user;
    isPaused = true;
    bottomView = new HBox();
    bottomView.setAlignment(Pos.TOP_CENTER);
    bottomView.setPadding(new Insets(PADDING));
    bottomView.setSpacing(X_SPACING);
    bottomView.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    bottomView.getStyleClass().add("root");
    bottomView.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    initiateBottomView(bottomView);
  }

  private HBox makeSpeedSlider() {
    HBox sliderBox = new HBox();
    sliderBox.setAlignment(Pos.CENTER);
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

  private ImageView makeButtonImage(String path, int size) {
    ImageView image = new ImageView(new Image(new File(path).toURI().toString()));
    image.setPreserveRatio(true);
    image.setFitHeight(size);
    return image;
  }

  private void togglePlayPause() {
    myController.pauseOrResume();
    if (isPaused) {
      playPauseButton.setImage(new Image(new File(String.format("%s%s", IMAGE_PATH, PAUSE_IMAGE)).toURI().toString()));
      isPaused = false;
    } else {
      playPauseButton.setImage(new Image(new File(String.format("%s%s", IMAGE_PATH, PLAY_IMAGE)).toURI().toString()));
      isPaused = true;
    }
  }

  private void initiateBottomView(HBox root) {
    VBox leftSide = makeProfileInfo();
    ImageView saveButton = makeGraphicButton("saveButton-" + myResources.getString("save") + ".png",
            e -> saveGame(), GRAPHIC_BUTTON_HEIGHT, SAVE_BUTTON_ID);
    ImageView statsButton = makeGraphicButton("statsButton-" + myResources.getString("stats") + ".png",
            e -> showStats(), GRAPHIC_BUTTON_HEIGHT, STATS_BUTTON_ID);
    ImageView restartButton = makeGraphicButton("restartButton-" + myResources.getString("restart") + ".png",
            e -> restartGame(), GRAPHIC_BUTTON_HEIGHT, NEW_GAME_BUTTON_ID);
    VBox graphicButtons = new VBox();
    graphicButtons.setSpacing(Y_SPACING);
    graphicButtons.getStyleClass().add("graphic-buttons");
    graphicButtons.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    graphicButtons.getChildren().addAll(saveButton, statsButton, restartButton);
    playPauseButton = makeGraphicButton(PLAY_IMAGE, e -> togglePlayPause(), SIM_BUTTON_SIZE, PLAY_PAUSE_BUTTON_ID);
    ImageView stepButton = makeGraphicButton(STEP_IMAGE, e -> togglePlayPause(), SIM_BUTTON_SIZE, STEP_BUTTON_ID);
    HBox buttonsPane = new HBox();
    buttonsPane.setAlignment(Pos.TOP_CENTER);
    buttonsPane.setSpacing(X_SPACING);
    buttonsPane.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    buttonsPane.getStyleClass().add("root");
    buttonsPane.getChildren().addAll(graphicButtons, playPauseButton, stepButton);
    HBox slider = makeSpeedSlider();
    VBox rightSide = new VBox();
    rightSide.setAlignment(Pos.TOP_CENTER);
    rightSide.setSpacing(10);
    rightSide.getChildren().addAll(buttonsPane, slider);
    rightSide.setBackground(new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    root.getChildren().addAll(leftSide,rightSide);
  }

  private VBox makeProfileInfo() {
    VBox profileInfo = new VBox();
    profileInfo.setSpacing(4);
    profileInfo.setAlignment(Pos.TOP_CENTER);
    HBox pfpBorder = new HBox();
    ImageView profilePic = new ImageView(new Image(new File(myUser.imagePath()).toURI().toString()));
    double sideLen = Math.min(profilePic.getFitWidth(), profilePic.getFitHeight());
    profilePic.setViewport(new Rectangle2D(0, 0, sideLen, sideLen));
    profilePic.setPreserveRatio(true);
    profilePic.setFitHeight(SIM_BUTTON_SIZE - (2 * PFP_BORDER_WIDTH));
    profilePic.setOnMouseReleased(e -> makeProfileView());
    pfpBorder.setAlignment(Pos.TOP_CENTER);
    pfpBorder.getChildren().add(profilePic);
    pfpBorder.setMaxWidth(SIM_BUTTON_SIZE - (2 * PFP_BORDER_WIDTH));
    pfpBorder.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
            new CornerRadii(PFP_BORDER_WIDTH), new BorderWidths(PFP_BORDER_WIDTH))));
    Text username = new Text(myUser.username().toUpperCase(Locale.ROOT));
    username.setFill(Color.LIGHTGRAY);
    username.setFont(Font.font("Verdana", FontWeight.BLACK, 30));
    profileInfo.getChildren().addAll(pfpBorder, username);
    return profileInfo;
  }

  private void saveGame() {
    try {
      myController.saveFile();
    } catch (IOException e) {
      new ErrorPopups(myLanguage,"SaveError");
    }
  }

  private ImageView makeGraphicButton(String filename, EventHandler handler, int height, String id) {
    String imagePath = String.format("%s%s", IMAGE_PATH, filename);
    ImageView myImgView = new ImageView(new Image(new File(imagePath).toURI().toString()));
    myImgView.setPreserveRatio(true);
    myImgView.setFitHeight(height);
    myImgView.setOnMouseReleased(handler);
    myImgView.setId(id);
    return myImgView;
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

  private void makeProfileView() {
    Stage newStage = new Stage();
    new UserInformationView(myController, myUser, newStage, myLanguage);
  }

  private void restartGame() {
    // TODO: implement resetGame function here
    myController.restartGame(myStage);
  }

  /**
   * Getter method to return the VBox that holds all the buttons and other Nodes to be placed at the
   * bottom of the screen.
   *
   * @return bottomView is a Node with the items to be placed on the bottom of the screen.
   */
  public HBox getBottomViewGP() {
    return bottomView;
  }

}

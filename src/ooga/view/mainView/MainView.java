package ooga.view.mainView;

import java.io.File;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.model.VanillaGame;
import ooga.model.util.GameStatus;
import ooga.view.bottomView.BottomView;
import ooga.view.center.BoardView;
import ooga.view.popups.GameEndPopups;
import ooga.view.startupView.GameStartupPanel;
import ooga.view.topView.TopView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to create the MainView that places all the other View classes in the correct locations in
 * the BorderPane in MainView, which is displayed to the user.
 *
 * @author Dane Erickson
 */
public class MainView {

  public static final int SCENE_WIDTH = 650;
  public static final int SCENE_HEIGHT = 750;
  public static final int BOARD_WIDTH = 600;
  public static final String MAINVIEW_PACKAGE = "ooga.view.mainView.";
  public static final Color BG_COLOR = Color.TRANSPARENT;
  private static final Logger LOG = LogManager.getLogger(MainView.class);

  private BottomView myBottomView;
  private TopView myTopView;
  private BoardView myBoardView;
  private Controller myController;
  private Stage myStage;
  private Scene myScene;
  private VanillaGame myGame;
  private BorderPane root;
  private Consumer<GameStatus> gameEndConsumer = status -> {
    if (status == GameStatus.WIN) {
      new GameEndPopups().winLosePopup("win", 0, 0);
    } else if (status == GameStatus.LOSS) {
      new GameEndPopups().winLosePopup("lose", 0, 0);
    }
  };
  private GameStartupPanel gameStartupPanel;
  private String STYLESHEET;

  /**
   * Constructor to create a MainView object to make the scene based on the constructed BorderPane
   * with each view object in the correct location.
   *
   * @param controller      is the Controller object used to communicate between the model and view
   * @param game            is the VanillaGame object that is used in the model
   * @param stage           is the Stage where the scene from MainView is set
   * @param userPreferences is the UserPreferences object from the uploaded file
   */
  public MainView(Controller controller, VanillaGame game, Stage stage, String selectedViewMode,
      UserPreferences userPreferences) {
    this.STYLESHEET = "/ooga/view/resources/" + selectedViewMode + ".css";
    myController = controller;
    controller.setAnimationSpeed(1);
    myGame = game;
    myGame.getBoard().addGameStatusConsumer(gameEndConsumer);
    myBottomView = new BottomView(myController, myGame, userPreferences.language());
//    gameStartupPanel = new GameStartupPanel(myStage);
    myStage = stage;
    myStage.setTitle("PACMAN");
    Image favicon = new Image(new File("data/images/pm_favicon.png").toURI().toString());
    myStage.getIcons().add(favicon);
    myBoardView = new BoardView(myGame, myController, userPreferences);
    myTopView = new TopView(myGame, userPreferences.language());
    myScene = makeScene();
//    myStage.hide();
    myStage.setScene(myScene);
    myStage.show();
  }

  private Scene makeScene() {
    Group myGroup = new Group();
    Rectangle bgRect = new Rectangle(0, 0, SCENE_WIDTH, SCENE_HEIGHT);
    bgRect.setId("myBackgroundColor");
    root = new BorderPane();
    root.setBackground(
        new Background(new BackgroundFill(BG_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    setStyles();
    Node centerNode = myBoardView.getBoardPane();
    root.setCenter(centerNode);
    root.setBottom(myBottomView.getBottomViewGP());
    root.setTop(myTopView.getTopViewGP());
    BorderPane.setAlignment(myTopView.getTopViewGP(), Pos.BOTTOM_CENTER);
    myGroup.getChildren().addAll(bgRect, root);
    root.setPadding(new Insets(0, 0, 0, (SCENE_WIDTH - BOARD_WIDTH) / 2));
    Scene scene = new Scene(myGroup, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    scene.setOnKeyPressed(e -> {
      myController.updatePressedKey(e);
//      LOG.info("key was pressed");
    });
    return scene;
  }

  private void setStyles() {
    root.getStyleClass().add("root");
    root.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
  }
}

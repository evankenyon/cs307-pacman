package ooga.view.mainView;

import java.util.List;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.IO.UserPreferences;
import ooga.model.VanillaGame;
import ooga.model.util.Position;
import ooga.view.GameStartupPanel;
import ooga.view.bottomView.BottomView;
import ooga.view.center.BoardView;
import ooga.view.topView.TopView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainView {

  public static final int SCENE_WIDTH = 1000;
  public static final int SCENE_HEIGHT = 600;

  private BottomView myBottomView;
  private TopView myTopView;
  private BoardView myBoardView;
  private Controller myController;
  private Stage myStage;
  private Scene myScene;
  private VanillaGame myGame;
  private BorderPane root;
  private GameStartupPanel gameStartupPanel;
  private static final Logger LOG = LogManager.getLogger(MainView.class);


  public MainView(Controller controller, VanillaGame game, Stage stage, UserPreferences userPreferences) {
    myController = controller;
    controller.setAnimationSpeed(1);
    myGame = game;
    myBottomView = new BottomView(myController, myGame);
//    gameStartupPanel = new GameStartupPanel(myStage);
    myStage = stage;
    myBoardView = new BoardView(myGame, myController, userPreferences);
    myTopView = new TopView(myGame);
    myScene = makeScene();
//    myStage.hide();
    myStage.setScene(myScene);
    myStage.show();
  }

  private Scene makeScene() {
    root = new BorderPane();
    root.setCenter(myBoardView.getBoardPane());
    root.setBottom(myBottomView.getBottomViewGP());
    root.setTop(myTopView.getTopViewGP());
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.setOnKeyPressed(e -> {
      myController.updatePressedKey(e);
//      LOG.info("key was pressed");
    });
    return scene;
  }
}

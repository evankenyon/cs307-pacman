package ooga.view.mainView;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.VanillaGame;
import ooga.view.bottomView.BottomView;
import ooga.view.center.BoardView;
import ooga.view.topView.TopView;

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

  public MainView (Controller controller, VanillaGame game) {
    myController = controller;
    myGame = game;
    myScene = makeScene(SCENE_WIDTH, SCENE_HEIGHT);
    myStage.setScene(myScene);
    myStage.show();
    myBottomView = new BottomView();
    myBoardView = new BoardView(myGame, myController);
    myTopView = new TopView();
  }

  private Scene makeScene(int width, int height) {
    root = new BorderPane();
    root.setCenter(myBoardView.getGridPane());
    root.setBottom(myBottomView.getBottomViewGP());
    root.setTop(myTopView.getTopViewGP());
    Scene scene = new Scene(root, width, height);
    return scene;
  }
}

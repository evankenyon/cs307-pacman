package ooga.view.mainView;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.model.VanillaGame;
import ooga.view.bottomView.BottomView;
import ooga.view.center.BoardView;

public class MainView {

  private BottomView myBottomView;
  private BoardView myBoardView;
  private Controller myController;
  private Stage myStage;
  private VanillaGame myGame;
  private BorderPane root;

  public MainView (Stage stage, Controller controller, VanillaGame game) {
    myController = controller;
    myStage = stage;
    myGame = game;
    myBottomView = new BottomView();
    myBoardView = new BoardView(myGame, myController);
  }

  public Scene makeScene(int width, int height) {
    root = new BorderPane();
    root.setCenter(myBoardView.getGridPane());
    root.setBottom(myBottomView.getGridPane());
    root.setTop(myTopView.getGridPane());
    Scene scene = new Scene(root, width, height);
    return scene;
  }
}

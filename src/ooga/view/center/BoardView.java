package ooga.view.center;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.controller.Controller;
import ooga.controller.IO.JsonParser;
import ooga.model.VanillaGame;
import ooga.model.agents.wall;
import ooga.model.util.Position;
import ooga.model.interfaces.Agent;
import ooga.view.center.agents.AgentView;
import ooga.view.center.agents.WallView;

public class BoardView {

  public static final int BOARD_WIDTH = 500;
  public static final int BOARD_HEIGHT = 700;
  public static final int GRID_SIZE = 1;
  public static final Paint BOARD_COLOR = Color.BLACK;

  private VanillaGame myGame;
  private Controller myController;
  private GridPane myBoardPane;

  public BoardView (VanillaGame game, Controller controller) {
    myGame = game;
    myController = controller;
    myBoardPane = new GridPane();
    myBoardPane.setBackground(new Background(new BackgroundFill(BOARD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    initiateBoard();
  }

  private void initiateBoard() {
//    makeWalls(myParser.getWallMapPositions());
//    int rows = myController.getRows();
//    int cols = myController.getCols();
//    for (int r=0; r<rows; r++) {
//      for (int c=0; c<cols; c++) {
//        Agent agent = myController.getAgent(x,y);
//        String agentType = agent.getType();
//        //TODO: reflection to create ItemView
//        makeAgentView(agentType);
//      }
//    }
    Map<String, List<Position>> agentMap = myController.getWallMap();
    for (String type : agentMap.keySet()) {
      for (Position p : agentMap.get(type)) {
        AgentView agentView = makeAgentView(type, p);
        attachAgent(agentView);
      }
    }
  }

//  private void updateBoard(Agent newInfo) {
//    myAgentView.updateAgent(newInfo);
//    GridPane.setColumnIndex(myAgentView.getImage(), myAgentView.getX());
//    GridPane.setColumnIndex(myAgentView.getImage(), myAgentView.getX());
//  }

  private void attachAgent(AgentView agentView) {
    myBoardPane.add(agentView.getImage(), agentView.getX(), agentView.getY());
  }

  private AgentView makeAgentView(String type, Position position) {
    String camelType = String.format("%s%s",type.substring(0,1).toUpperCase(),type.substring(1));
    String className = String.format("ooga.view.center.agents.%sView",camelType);
    Agent agent = myGame.getBoard().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class)
          .newInstance(agent);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      //TODO: remove stack trace
      e.printStackTrace();
      return new WallView(new wall(0,0));
    }
  }

  public Node getGridPane() { return myBoardPane; }

}

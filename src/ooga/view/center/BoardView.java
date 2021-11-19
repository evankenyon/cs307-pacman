package ooga.view.center;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
  private static final String DEFAULT_RESOURCE_PACKAGE =
      BoardView.class.getPackageName() + ".resources.";
  private static final String TYPE_FILENAME = "types";
  public static final double BOARD_WIDTH = 600;
  public static final double BOARD_HEIGHT = 400;
  public static final int GRID_SIZE = 1;
  public static final Paint BOARD_COLOR = Color.BLACK;

  private VanillaGame myGame;
  private Controller myController;
  private Pane myBoardPane;
  private List<Consumer<AgentView>> boardConsumerList;
  private double numRows;
  private double numCols;

  public BoardView (VanillaGame game, Controller controller, Map<String, List<Position>> wallMap) {
    myGame = game;
    myController = controller;
    myBoardPane = new Pane();
    boardConsumerList = new ArrayList<>();
    initiateBoard(wallMap);
    myBoardPane.setMaxWidth(BOARD_WIDTH);
    myBoardPane.setMaxHeight(BOARD_HEIGHT);
    myBoardPane.setBackground(new Background(new BackgroundFill(BOARD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
  }

  private void initiateBoard(Map<String, List<Position>> agentMap) {
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
    for (String type : agentMap.keySet()) {
      for (Position p : agentMap.get(type)) {
//        updateDimensions(p);
        AgentView agentView = makeAgentView(type, p);
        attachAgent(agentView);
      }
    }
  }

//  private void updateDimensions(Position p) {
//    if (p.getCoords()[0] > numCols) numCols = p.getCoords()[0];
//    if (p.getCoords()[1] > numRows) numRows = p.getCoords()[1];
//  }

//  private void updateBoard(AgentView newInfo) {
//    GridPane.setColumnIndex(newInfo.getImage(), newInfo.getX());
//    GridPane.setColumnIndex(newInfo.getImage(), newInfo.getX());
//  }

  private void attachAgent(AgentView agentView) {
    myBoardPane.getChildren().add(agentView.getImage());
  }

  private AgentView makeAgentView(String type, Position position) {
    ResourceBundle types = ResourceBundle.getBundle("ooga.view.center.resources.types");
    String className = String.format("ooga.view.center.agents.%sView",types.getString(type));
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
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

//  public double getDimension(int index) {
//    if (index == 0) return numCols;
//    return numRows;
//  }

  public Node getBoardPane() { return myBoardPane; }

}

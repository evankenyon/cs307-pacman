package ooga.view.center;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import ooga.controller.IO.UserPreferences;
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
  private static final String CONSTRUCTORS_FILENAME = "constructors";
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

  public BoardView (VanillaGame game, Controller controller, UserPreferences userPreferences) {
    myGame = game;
    myController = controller;
    myBoardPane = new Pane();
    boardConsumerList = new ArrayList<>();
    initiateBoard(userPreferences);
    myBoardPane.setMaxWidth(BOARD_WIDTH);
    myBoardPane.setMaxHeight(BOARD_HEIGHT);
    myBoardPane.setBackground(new Background(new BackgroundFill(BOARD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
  }

  private void initiateBoard(UserPreferences userPreferences) {
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
    for (String type : userPreferences.wallMap().keySet()) {
      for (Position p : userPreferences.wallMap().get(type)) {
//        updateDimensions(p);
        AgentView agentView = null;
        ResourceBundle types = ResourceBundle.getBundle("ooga.view.center.resources.types");
        String realType = types.getString(type);
        ResourceBundle constructors = ResourceBundle.getBundle(String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, CONSTRUCTORS_FILENAME));
        if(constructors.getString(type).equals("Color")) {
          agentView = makeAgentViewColor(realType, p, userPreferences.colors().get(type));
        } else {
          agentView = makeAgentViewImage(realType, p, userPreferences.imagePaths().get(type));
        }
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

  private AgentView makeAgentViewColor(String type, Position position, List<Double> rgb) {
    String className = String.format("ooga.view.center.agents.%sView", type);
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class, List.class)
          .newInstance(agent, rgb);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      return makeAgentView(type, position);
    }
  }

  private AgentView makeAgentViewImage(String type, Position position, String imagePath) {
    String className = String.format("ooga.view.center.agents.%sView", type);
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class, String.class)
          .newInstance(agent, imagePath);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      return makeAgentView(type, position);
    }
  }

  private AgentView makeAgentView(String type, Position position) {
    String className = String.format("ooga.view.center.agents.%sView", type);
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

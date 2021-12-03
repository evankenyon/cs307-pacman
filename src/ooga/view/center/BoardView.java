package ooga.view.center;

import static ooga.Main.LANGUAGE;

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
import javafx.scene.layout.*;
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
import ooga.view.popups.ErrorPopups;

/**
 * Class that creates the BoardView in the front end, which is a Pane with all the AgentView objects
 * placed in their corresponding location to create the Pac-Man board in the view.
 *
 * @author Dane Erickson
 */
public class BoardView {

  private static final String DEFAULT_RESOURCE_PACKAGE =
      BoardView.class.getPackageName() + ".resources.";
  private static final String TYPE_FILENAME = "types";
  private static final String CONSTRUCTORS_FILENAME = "constructors";
  public static final double BOARD_WIDTH = 600.;
  public static final double BOARD_HEIGHT = 400.;
  public static final Paint BOARD_COLOR = Color.BLACK;

  private VanillaGame myGame;
  private Controller myController;
  private Pane myBoardPane;
  private List<Consumer<AgentView>> boardConsumerList;
  private int numRows;
  private int numCols;
  private String myLanguage;

  /**
   * Constructor to create a BoardView object based on UserPreferences from the inputted file and
   * corresponding AgentView objects.
   *
   * @param game            is the model object that controls the back end Agents in the game
   * @param controller      is the Controller object that communicates between the view and model
   * @param userPreferences is the UserPreferences object with information from the uploaded files
   */
  public BoardView(VanillaGame game, Controller controller, UserPreferences userPreferences) {
    myGame = game;
    myController = controller;
    myBoardPane = new Pane();
    boardConsumerList = new ArrayList<>();
    numRows = userPreferences.rows();
    numCols = userPreferences.cols();
    myLanguage = userPreferences.language();
    initiateBoard(userPreferences);
    myBoardPane.setMaxWidth(BOARD_WIDTH);
    myBoardPane.setMaxHeight(BOARD_HEIGHT);
    myBoardPane.setBackground(
        new Background(new BackgroundFill(BOARD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    myBoardPane.setBorder(new Border(
            new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(10))));
  }

  private void initiateBoard(UserPreferences userPreferences) {
    System.out.println(userPreferences.wallMap().keySet().toString());
    for (String type : userPreferences.wallMap().keySet()) {
      for (Position p : userPreferences.wallMap().get(type)) {
        AgentView agentView = null;
        ResourceBundle types = ResourceBundle.getBundle("ooga.view.center.resources.types");
        String realType = types.getString(type);
        ResourceBundle constructors = ResourceBundle.getBundle(
            String.format("%s%s", DEFAULT_RESOURCE_PACKAGE, CONSTRUCTORS_FILENAME));
        if (constructors.getString(type).equals("Color")) {
          agentView = makeAgentViewColor(realType, p, userPreferences.colors().get(type));
        } else {
          agentView = makeAgentViewImage(realType, p, userPreferences.imagePaths().get(type));
        }
        attachAgent(agentView);
      }
    }
  }

  private void attachAgent(AgentView agentView) {
    myBoardPane.getChildren().add(agentView.getImage());
    assignOrder(agentView);
  }

  private void assignOrder(AgentView agentView) {
    String methodName = String.format("to%s", agentView.getOrder());
    try {
      Method m = Node.class.getDeclaredMethod(methodName, null);
      m.invoke(agentView.getImage(), null);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      new ErrorPopups(LANGUAGE, "reflectionError");
    }
  }

  private AgentView makeAgentViewColor(String type, Position position, List<Double> rgb) {
    String className = String.format("ooga.view.center.agents.%sView", type);
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class, List.class, int.class, int.class)
          .newInstance(agent, rgb, numRows, numCols);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      return makeAgentView(type, position);
    }
  }

  private AgentView makeAgentViewImage(String type, Position position, String imagePath) {
    String className = String.format("ooga.view.center.agents.%sView", type);
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class, String.class, int.class,
              int.class)
          .newInstance(agent, imagePath, numRows, numCols);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      return makeAgentView(type, position);
    }
  }

  private AgentView makeAgentView(String type, Position position) {
    String className = String.format("ooga.view.center.agents.%sView", type);
    Agent agent = myGame.getBoard().getGameState().findAgent(position);
    try {
      Class<?> clazz = Class.forName(className);
      return (AgentView) clazz.getDeclaredConstructor(Agent.class, int.class, int.class)
          .newInstance(agent, numRows, numCols);
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
      e.printStackTrace();
      return new WallView(new wall(position.getCoords()[0], position.getCoords()[1]), numRows,
          numCols);
    }
  }

  /**
   * Getter method to get the Pane with all the AgentView objects placed in the correct locations.
   * This is used in MainView to place the BoardView Pane in the center position of MainView's
   * BorderPane.
   *
   * @return myBoardPane is a Node that has all the AgentView objects placed at the correct
   * locations.
   */
  public Node getBoardPane() {
    return myBoardPane;
  }

}

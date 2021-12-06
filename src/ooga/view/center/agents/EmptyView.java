package ooga.view.center.agents;

import static ooga.model.agents.wall.UNPASSABLE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.model.interfaces.Agent;

/**
 * Subclass of StationaryView, which is a subclass of AgentView. WallView creates a View Agent that
 * shows the walls in the game.
 *
 * @author Dane Erickson
 */
public class EmptyView extends StationaryView {

  public static final Paint EMPTY_COLOR = Color.TRANSPARENT;

  private Agent myAgent;
  private Rectangle myEmptyShape;
  private Consumer<Agent> updateEmpty = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;

  /**
   * Constructor to create the WallView object using the default wall color for the Rectangles in
   * WallView
   *
   * @param empty    is the Agent from the backend that corresponds to the front end Agent
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public EmptyView(Agent empty, int gridRows, int gridCols) {
    myAgent = empty;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    wallViewSetup(EMPTY_COLOR);
  }

  /**
   * Constructor to create the WallView object using the inputted list of RGB values to determine an
   * inputted color for the walls from the user.
   *
   * @param w        is the Agent from the backend that corresponds to the front end Agent
   * @param rgb      is the list of Doubles that represent the red, green, and blue values to
   *                 determine the color - not used in EmptyView since it's transparent
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public EmptyView(Agent w, List<Double> rgb, int gridRows, int gridCols) {
    myAgent = w;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    wallViewSetup(EMPTY_COLOR);
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
  }

  private void wallViewSetup(Paint color) {
    myEmptyShape = new Rectangle(gridWidth, gridHeight, color);
    setImage(myEmptyShape);
    myEmptyShape.setX(gridWidth * myAgent.getPosition().getCoords()[0]);
    myEmptyShape.setY(gridHeight * myAgent.getPosition().getCoords()[1]);
    myAgent.addConsumer(updateEmpty);
  }

  @Override
  protected void updateState(int newState) {
    myEmptyShape.setVisible(newState == UNPASSABLE);
  }
}

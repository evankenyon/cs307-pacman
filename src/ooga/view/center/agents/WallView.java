package ooga.view.center.agents;

import static ooga.model.agents.Wall.UNPASSABLE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.Arrays;
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
public class WallView extends StationaryView {

  public static final List<Double> WALL_COLOR_RGB = Arrays.asList(0., 0., 255.);
  public static final Paint WALL_COLOR = Color.BLUE;

  private Agent myAgent;
  private Rectangle myWallShape;
  private Consumer<Agent> updateWall = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;

  /**
   * Constructor to create the WallView object using the default Wall color for the Rectangles in
   * WallView
   *
   * @param w        is the Agent from the backend that corresponds to the front end Agent
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public WallView(Agent w, int gridRows, int gridCols) {
//    this(w, WALL_COLOR_RGB, gridRows, gridCols);
    myAgent = w;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    wallViewSetup(WALL_COLOR);
  }

  /**
   * Constructor to create the WallView object using the inputted list of RGB values to determine an
   * inputted color for the walls from the user.
   *
   * @param w        is the Agent from the backend that corresponds to the front end Agent
   * @param rgb      is the list of Doubles that represent the red, green, and blue values to
   *                 determine the Wall's color
   * @param gridRows is the row position of the Agent
   * @param gridCols is the column position of the Agent
   */
  public WallView(Agent w, List<Double> rgb, int gridRows, int gridCols) {
    myAgent = w;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    Color wallColor = new Color(rgb.get(0), rgb.get(1), rgb.get(2), 1);
    wallViewSetup(wallColor);
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
  }

  private void wallViewSetup(Paint color) {
    myWallShape = new Rectangle(gridWidth, gridHeight, color);
    setImage(myWallShape);
    myWallShape.setX(gridWidth * myAgent.getPosition().getCoords()[0]);
    myWallShape.setY(gridHeight * myAgent.getPosition().getCoords()[1]);
    myAgent.addConsumer(updateWall);
  }

  @Override
  protected void updateState(int newState) {
    myWallShape.setVisible(newState == UNPASSABLE);
  }
}

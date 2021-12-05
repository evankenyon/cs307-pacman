package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;
import static ooga.view.center.agents.PelletView.PELLET_COLOR;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import ooga.model.interfaces.Agent;

/**
 * Subclass of StationaryView, which is a subclass of AgentView. SuperPelletView creates a View
 * Agent that shows the super pellets in the game.
 *
 * @author Dane Erickson
 */
public class SuperPelletView extends StationaryView {

  public static final double LARGE_PELLET_SIZE = 0.25; // large pellets radii are 50% of grid square

  //  private VanillaGame myGame;
//  private Position myInfo;
  private Agent myAgent; //TODO: change to correct agent subclass
  private Circle myCircle;
  private Consumer<Agent> updatePellet = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;
  private double pelletBufferX;
  private double pelletBufferY;

  /**
   * Constructor to create the SuperPelletView object using the default pellet color for the Circles
   * in SuperPelletView
   *
   * @param pelletAgent is the Agent from the backend that corresponds to the front end Agent
   * @param gridRows    is the row position of the Agent
   * @param gridCols    is the column position of the Agent
   */
  public SuperPelletView(Agent pelletAgent, int gridRows, int gridCols) {
//    this(pelletAgent, PELLET_COLOR, gridRows, gridCols);
    myAgent = pelletAgent;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    myCircle = makeCircle(PELLET_COLOR);
    superPelletViewSetup();
  }

  /**
   * Constructor to create the SuperPelletView object using the inputted list of RGB values to
   * determine an inputted color for the super pellets from the user.
   *
   * @param pelletAgent is the Agent from the backend that corresponds to the front end Agent
   * @param rgb         is the list of Doubles that represent the red, green, and blue values to
   *                    determine the super pellet's color
   * @param gridRows    is the row position of the Agent
   * @param gridCols    is the column position of the Agent
   */
  public SuperPelletView(Agent pelletAgent, List<Double> rgb, int gridRows, int gridCols) {
    myAgent = pelletAgent;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    // TODO: move these values to a props file
    Color pelletColor = new Color(rgb.get(0), rgb.get(1), rgb.get(2), 1);
    myCircle = makeCircle(pelletColor);
    superPelletViewSetup();
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
    pelletBufferX = gridWidth / 2;
    pelletBufferY = gridHeight / 2;
  }

  private void superPelletViewSetup() {
    setImage(myCircle);
    myCircle.setCenterX(gridWidth * myAgent.getPosition().getCoords()[0] + pelletBufferX);
    myCircle.setCenterY(gridHeight * myAgent.getPosition().getCoords()[1] + pelletBufferY);
    myAgent.addConsumer(updatePellet);
  }

  private Circle makeCircle(Paint color) {
    double x = myAgent.getPosition().getCoords()[0];
    double y = myAgent.getPosition().getCoords()[1];
    return new Circle(x, y, Math.min(gridHeight, gridWidth) * LARGE_PELLET_SIZE, color);
  }

  @Override
  protected void updateState(int newState) {
    myCircle.setVisible(newState == ALIVE_STATE);
  }
}

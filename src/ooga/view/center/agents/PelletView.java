package ooga.view.center.agents;

import static ooga.model.agents.players.Pacman.ALIVE_STATE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.function.Consumer;
import javafx.scene.shape.Circle;
import ooga.model.interfaces.Agent;

public class PelletView extends StationaryView {

  public static final List<Double> PELLET_COLOR_RGB = Arrays.asList(255.,255.,255.);
  public static final Paint PELLET_COLOR = Color.WHITE;
  public static final double SMALL_PELLET_SIZE = 0.10; // small pellets radii are 10% of grid square

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

  public PelletView (Agent pelletAgent, int gridRows, int gridCols) {
//    this(pelletAgent, PELLET_COLOR_RGB, gridRows, gridCols);
    numRows = gridRows;
    numCols = gridCols;
    myAgent = pelletAgent;
    makeLayoutSettings();
    myCircle = makeCircle(PELLET_COLOR);
    pelletViewSetup();
  }

  public PelletView(Agent pelletAgent, List<Double> rgb, int gridRows, int gridCols) {
    numRows = gridRows;
    numCols = gridCols;
    myAgent = pelletAgent;
    makeLayoutSettings();
    // TODO: move these values to a props file
    Color pelletColor = new Color(rgb.get(0), rgb.get(1), rgb.get(2), 1);
    myCircle = makeCircle(pelletColor);
    pelletViewSetup();
  }

  private void makeLayoutSettings() {
    gridWidth = BOARD_WIDTH / numCols;
    gridHeight = BOARD_HEIGHT / numRows;
    imageBuffer = IMAGE_BUFFER_FACTOR * Math.min(gridWidth, gridHeight);
    pelletBufferX = gridWidth / 2;
    pelletBufferY = gridHeight / 2;
  }

  private void pelletViewSetup() {
    setImage(myCircle);
    myCircle.setCenterX(gridWidth*myAgent.getPosition().getCoords()[0] + pelletBufferX);
    myCircle.setCenterY(gridHeight*myAgent.getPosition().getCoords()[1] + pelletBufferY);
    myAgent.addConsumer(updatePellet);
  }

  private Circle makeCircle(Paint color) {
    int x = myAgent.getPosition().getCoords()[0];
    int y = myAgent.getPosition().getCoords()[1];
    return new Circle(x, y, Math.min(gridHeight, gridWidth) * SMALL_PELLET_SIZE, color);
  }

  @Override
  protected void updateState(int newState) {
    myCircle.setVisible(newState == ALIVE_STATE);
  }
}

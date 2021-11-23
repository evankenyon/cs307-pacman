package ooga.view.center.agents;

import static ooga.model.agents.wall.UNPASSABLE;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.model.interfaces.Agent;

public class WallView extends StationaryView {

  public static final List<Double> WALL_COLOR_RGB = Arrays.asList(0.,0.,255.);
  public static final Paint WALL_COLOR = Color.BLUE;

  private Agent myAgent;
  private Rectangle myWallShape;
  private Consumer<Agent> updateWall = newInfo -> updateAgent(newInfo);
  private int numCols;
  private int numRows;
  private double gridWidth;
  private double gridHeight;
  private double imageBuffer;

  public WallView (Agent w, int gridRows, int gridCols) {
//    this(w, WALL_COLOR_RGB, gridRows, gridCols);
    myAgent = w;
    numCols = gridCols;
    numRows = gridRows;
    makeLayoutSettings();
    wallViewSetup(WALL_COLOR);
  }

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
    myWallShape.setX(gridWidth*myAgent.getPosition().getCoords()[0]);
    myWallShape.setY(gridHeight*myAgent.getPosition().getCoords()[1]);
    myAgent.addConsumer(updateWall);
  }

  @Override
  protected void updateState(int newState) {
    myWallShape.setVisible(newState == UNPASSABLE);
  }
}

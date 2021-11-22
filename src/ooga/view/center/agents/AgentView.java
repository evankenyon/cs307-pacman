package ooga.view.center.agents;

import static ooga.controller.Controller.COLS;
import static ooga.controller.Controller.ROWS;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import javafx.scene.Node;
import ooga.model.interfaces.Agent;

public abstract class AgentView {

  public static final double GRID_WIDTH = BOARD_WIDTH / COLS;
  public static final double GRID_HEIGHT = BOARD_HEIGHT / ROWS;
  public static final double GRID_MIN = Math.min(GRID_HEIGHT,GRID_WIDTH);
  public static final double IMAGE_BUFFER = GRID_MIN*0.9; // Images fill 90% of grid squares
  public static final double VERTICAL_IMAGE_BUFFER = (GRID_HEIGHT-IMAGE_BUFFER)/2;
  public static final double HORIZONTAL_IMAGE_BUFFER = (GRID_WIDTH-IMAGE_BUFFER)/2;

  private Node myImage;

  public abstract void updateAgent(Agent agent);

  public Node getImage() { return myImage; }

  protected void setImage(Node newImage) { myImage = newImage; }

}

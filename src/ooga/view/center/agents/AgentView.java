package ooga.view.center.agents;

import static ooga.controller.Controller.cols;
import static ooga.controller.Controller.rows;
import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import javafx.scene.Node;
import ooga.model.interfaces.Agent;

public abstract class AgentView {

  public static final double GRID_WIDTH = BOARD_WIDTH / cols;
  public static final double GRID_HEIGHT = BOARD_HEIGHT / rows;
  public static final double GRID_MIN = Math.min(GRID_HEIGHT,GRID_WIDTH);

  private Node myImage;
  private int myX;
  private int myY;

  public abstract void updateAgent(Agent agent);

  public Node getImage() { return myImage; }

  protected void setImage(Node newImage) { myImage = newImage; }

  public int getX() { return myX; }

  protected void setX(int newX) { myX = newX; }

  public int getY() { return myY; }

  protected void setY(int newY) { myY = newY; }

}

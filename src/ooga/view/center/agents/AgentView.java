package ooga.view.center.agents;

import static ooga.view.center.BoardView.BOARD_HEIGHT;
import static ooga.view.center.BoardView.BOARD_WIDTH;

import javafx.scene.Node;
import ooga.model.interfaces.Agent;

public abstract class AgentView {

  public static final double IMAGE_BUFFER_FACTOR = 0.9; // Images fill 90% of grid squares

  private Node myImage;

  public abstract void updateAgent(Agent agent);

  public Node getImage() { return myImage; }

  protected void setImage(Node newImage) { myImage = newImage; }

}

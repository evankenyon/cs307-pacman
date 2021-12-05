package ooga.view.center.agents;

import javafx.scene.Node;
import ooga.model.interfaces.Agent;
import ooga.view.center.agents.interfaces.AgentViewInterface;

/**
 * This is the super class for all the items placed on the Pac-Man Board screen in the view. Each
 * subclass is a specific type of agent added to the screen and is created with reflection based on
 * the Agent type. The two subclasses separate movable and stationary items, and the individual
 * agents are subclasses of those two subclasses.
 *
 * @author Dane Erickson
 */
public abstract class AgentView implements AgentViewInterface {

  public static final double IMAGE_BUFFER_FACTOR = 0.9; // Images fill 90% of grid squares

  private Node myImage;
  private String myOrder;

  /**
   * Abstract method that is called when the consumer for each agent is called with .accept(). This
   * is overridden in MovableView and StationaryView depending on the action each agent should take
   * with the consumer is called.
   *
   * @param agent is the Agent interface of the agent to be updated
   */
  public abstract void updateAgent(Agent agent);

  /**
   * Getter method to get the image for each AgentView
   *
   * @return Node myImage that is the image of the AgentView
   */
  public Node getImage() {
    return myImage;
  }

  protected void setImage(Node newImage) {
    myImage = newImage;
  }

  /**
   * Getter method to get the order of the AgentView for the BoardView.
   * StationaryView Objects (pellets and fruit) are in the back.
   * MovableView Objects (Pacman and Ghosts) are in the front.
   *
   * @return myOrder is a String of it's position
   */
  public String getOrder() { return myOrder; }

  protected void setOrder(String order) { myOrder = order; }

}

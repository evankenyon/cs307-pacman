package ooga.view.center.agents;

import java.util.Map;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

/**
 * Subclass of AgentView and is the super class for the view agents that move on the screen
 *
 * @author Dane Erickson
 */
public abstract class MovableView extends AgentView {

  public static final String IMAGE_PATH = "data/images/";
  public static final Map<String, Integer> ORIENTATION_MAP =
      Map.of("right", 0, "down", 90, "left", 180, "up", 270, "NULL", 0);

  protected abstract void moveX(int x);

  protected abstract void moveY(int y);

  protected abstract void updateState(int state);

  protected abstract void updateOrientation(String orientation);

  /**
   * Overridden method from AgentView super class that changes the x and y positions, image (state),
   * and orientation of the image when the consumer for the corresponding Agent is called with
   * accept().
   *
   * @param agent is the Agent interface of the agent to be updated
   */
  @Override
  public void updateAgent(Agent agent) {
    Position p = agent.getPosition();
    int newState = agent.getState();
    moveX(p.getCoords()[0]);
    moveY(p.getCoords()[1]);
    updateState(newState);
    updateOrientation(p.getDirection());
  }

}

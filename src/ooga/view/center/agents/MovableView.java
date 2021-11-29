package ooga.view.center.agents;

import java.util.Map;
import ooga.model.interfaces.Agent;
import ooga.model.util.Position;

public abstract class MovableView extends AgentView {

  public static final String IMAGE_PATH = "data/images/";
  public static final Map<String, Integer> ORIENTATION_MAP =
      Map.of("right",0,"down",90,"left",180,"up",270,"NULL", 0);

  protected abstract void moveX(int x);

  protected abstract void moveY(int y);

  protected abstract void updateState(int state);

  protected abstract void updateOrientation(String orientation);

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

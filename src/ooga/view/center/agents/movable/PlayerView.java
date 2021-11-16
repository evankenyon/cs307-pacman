package ooga.view.center.agents.movable;

import ooga.model.interfaces.Agent;
import ooga.view.center.agents.AgentView;

public abstract class PlayerView extends AgentView {

  public static final String IMAGE_PATH = "ooga.view.center.images.";

  protected abstract void moveX(int x);

  protected abstract void moveY(int y);

  protected abstract void updateState(int state);

//  protected abstract void consume(PlayerView prey);

  protected void updatePlayer(Agent newInfo) {
    int newX = newInfo.getX();
    int newY = newInfo.getY();
    int newState = newInfo.getState();
    moveX(newX);
    moveY(newY);
    updateState(newState);
  }

}

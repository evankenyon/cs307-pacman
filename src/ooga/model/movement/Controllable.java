package ooga.model.movement;

import ooga.controller.IO.keyTracker;
import ooga.model.interfaces.Movable;
import ooga.model.util.AgentInfo;

/**
 * Allows agent to be controlled via keystrokes.
 */
public class Controllable implements Movable {

  private final keyTracker myKeyTracker;

  /**
   * Constructor for controllable intializing keytracker
   */
  public Controllable() {
    myKeyTracker = new keyTracker();
  }

  @Override
  public AgentInfo move(AgentInfo info) {
    int newX = myKeyTracker.getNewX(info.getX());
    int newY = myKeyTracker.getNewY(info.getY());
    return new AgentInfo(newX, newY, info.getState());
  }

  @Override
  public boolean isNull() {
    return false;
  }
}

package ooga.model.movement;

import ooga.model.Movable;
import ooga.model.util.AgentInfo;

/**
 * Implements not moving for agents.
 */
public class Static implements Movable {

  @Override
  public AgentInfo move(AgentInfo info) {
    return null;
  }

  @Override
  public boolean isNull() {
    return true;
  }
}

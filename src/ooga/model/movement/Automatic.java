package ooga.model.movement;

import ooga.model.Movable;
import ooga.model.util.AgentInfo;

/**
 * Implements one type of automatic movement for agent.
 */
public class Automatic implements Movable {

  @Override
  public AgentInfo move(AgentInfo info) {
    //some movement algorithm?
    return null;
  }

  @Override
  public boolean isNull() {
    return false;
  }
}

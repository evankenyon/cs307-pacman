package ooga.model;

import ooga.model.util.AgentInfo;

/**
 * Interface to allow movement to be automatically handled by an algorithm
 **/
public interface Movable {

  /**
   * return movement of object for one step of the game
   **/
  AgentInfo move(AgentInfo info);

  /**
   * @return if this is a null object (for agents that don't move)
   */
  boolean isNull();

}

package ooga.model.interfaces;

/**
 * Interface to allow controller to translate keystrokes into movement for an agent
 **/
public interface Controllable extends Agent {

  /**
   * given a string containing the direction pressed, updates the directions of the agent
   **/
  void setDirection(String direction);

}

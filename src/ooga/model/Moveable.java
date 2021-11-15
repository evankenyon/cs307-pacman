package ooga.model;

/**
 * Interface to allow movement to be automatically handled by an algorithm
 **/
public interface Moveable extends Agent {

  /**
   * return movement of object for one step of the game
   **/
  void move();

}

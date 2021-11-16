package ooga.model.interfaces;
import ooga.model.util.Position;

/**
 * Interface to allow movement to be automatically handled by an algorithm
 **/
public interface Movable {

  /**
   * return movement of object for one step of the game
   **/
  Position move(Position pos);

  /**
   * @return if this is a null object (for agents that don't move)
   */
  boolean isNull();

}

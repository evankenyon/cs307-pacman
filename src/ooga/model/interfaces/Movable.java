package ooga.model.interfaces;

import ooga.model.GameState;
import ooga.model.util.Position;

/**
 * Interface to allow movement to be automatically handled by an algorithm
 **/
public interface Movable {

  /**
   * return movement of object for one step of the game
   *
   * @param state current game state
   * @param currentPosition current object position
   */
  Position move(GameState state, Position currentPosition);
}

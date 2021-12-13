package ooga.model.interfaces;

import ooga.model.GameState;

/**
 * Inteface for end condition object.
 */
public interface EndCondition {

  /**
   * checks if game has ended depending on the end condition.
   *
   * @param state current game state
   * @return boolean if game has ended.
   */
  Boolean isEnd(GameState state);

}

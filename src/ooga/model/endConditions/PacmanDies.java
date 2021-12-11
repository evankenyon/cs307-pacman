package ooga.model.endConditions;

import ooga.model.GameState;
import ooga.model.interfaces.EndCondition;

/**
 * End Condition where game ends when Pacman Dies.
 */
public class PacmanDies implements EndCondition {

  @Override
  public Boolean isEnd(GameState state) {
    if (state.getLives() == 0) {
      return true;
    }
    return false;
  }
}

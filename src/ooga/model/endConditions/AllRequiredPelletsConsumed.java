package ooga.model.endConditions;

import ooga.model.GameState;
import ooga.model.interfaces.EndCondition;

public class AllRequiredPelletsConsumed implements EndCondition {

  @Override
  public Boolean isEnd(GameState state) {
    if (state.getRequiredPelletsLeft() == 0) {
      return true;
    }
    return false;
  }
}

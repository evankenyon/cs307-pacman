package ooga.model.endConditions;

import ooga.model.GameState;
import ooga.model.interfaces.EndCondition;

public class EndConditionContext {

  private EndCondition endCondition;

  public EndConditionContext() {
    endCondition = null;
  }

  //to get it to work through reflection, call setStrategy on an agent when looking at data file
  public void setStrategy(EndCondition strategyType) {
    endCondition = strategyType;
  }

  public Boolean checkEnd(GameState state) {
    return endCondition.isEnd(state);
  }

}

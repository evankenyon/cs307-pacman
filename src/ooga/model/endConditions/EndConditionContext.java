package ooga.model.endConditions;

import ooga.model.GameState;
import ooga.model.interfaces.EndCondition;

/**
 * Context object for setting end conditions.
 */
public class EndConditionContext {

  private EndCondition endCondition;

  /**
   * Constructor for context, sets initial endCondition to null.
   */
  public EndConditionContext() {
    endCondition = null;
  }

  /**
   * Set end condition strategy
   *
   * @param strategyType of EndCondition class.
   */
  public void setStrategy(EndCondition strategyType) {
    endCondition = strategyType;
  }

  /**
   * Check end of game with set end condition.
   *
   * @param state current GameState
   * @return boolean for end of game.
   */
  public Boolean checkEnd(GameState state) {
    return endCondition.isEnd(state);
  }

}

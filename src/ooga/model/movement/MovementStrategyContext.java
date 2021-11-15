package ooga.model.movement;

import ooga.model.Movable;
import ooga.model.util.AgentInfo;

/**
 * Implementing the strategy design pattern to decide how agents are going to move.
 */
public class MovementStrategyContext {

  private Movable strategy;

  /**
   * Constructor for a strategy context
   *
   * @param strategyType chosen strategy
   */
  public MovementStrategyContext(Movable strategyType) {
    strategy = strategyType;
  }

  /**
   * Move one step for the given agent with the given strategy.
   *
   * @param info old agentInfo state
   * @return new agentInfo state
   */
  public AgentInfo move(AgentInfo info) {
    return strategy.move(info);
  }
}

package ooga.model.movement;

import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

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
   * @param pos old agent position
   * @return new agentInfo state
   */
  public Position move(Position pos) {
    return strategy.move(pos);
  }
}

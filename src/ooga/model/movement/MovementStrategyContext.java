package ooga.model.movement;

import ooga.model.GameState;
import ooga.model.interfaces.Movable;
import ooga.model.util.Position;

/**
 * Implementing the strategy design pattern to decide how agents are going to move.
 */
public class MovementStrategyContext {

  private Movable strategy;


  /**
   * Constructor for a strategy context
   */
  public MovementStrategyContext() {
    strategy = null;
  }

  //to get it to work through reflection, call setStrategy on an agent when looking at data file
  public void setStrategy(Movable strategyType) {
    strategy = strategyType;
  }

  /**
   * Move one step for the given agent with the given strategy.
   *
   * @param pos old agent position
   * @return new agentInfo state
   */
  public Position move(GameState state, Position pos) {
    return strategy.move(state, pos);
  }
}

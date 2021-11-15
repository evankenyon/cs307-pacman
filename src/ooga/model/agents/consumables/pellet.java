package ooga.model.agents.consumables;

import java.util.function.Consumer;
import ooga.model.interfaces.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class pellet extends AbstractAgent implements Consumable {

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;

  private int myState;
  private Position myPosition;
  private MovementStrategyContext myMover;

  /**
   * abstract constructor for cell
   *
   * @param x     int x position
   * @param y     int y position
   * @param state int cell state
   */
  public pellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
  }

  @Override
  public void step() {
    myMover.move(myPosition);
  }

  @Override
  public void consume() {

  }

  @Override
  public void agentReact() {

  }

  @Override
  public void applyEffects() {

  }
}

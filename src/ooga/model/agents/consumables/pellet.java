package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
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
  public pellet(int x, int y, int state) {
    super(x, y, "PELLET");
    myState = UNEATEN_STATE;
  }

  @Override
  public Position step() {
    return myMover.move(myPosition);
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

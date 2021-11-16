package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
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
   */
  public pellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
    myMover = new MovementStrategyContext(new Static());
  }

  @Override
  public Position step() {
    return myMover.move(myPosition);
  }

  @Override
  public void setCoords(Position newPosition) {
    myPosition = newPosition;
  }


  @Override
  public void agentReact() {
    myState = EATEN_STATE;
    updateConsumer();
  }

  @Override
  public void applyEffects() {
  }

  @Override
  public int applyPoints() {
    return 2;
  }
}

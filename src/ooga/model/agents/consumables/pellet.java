package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class pellet extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 2;

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;

  private int myState;
  private MovementStrategyContext myMover;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   */
  public pellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
    myMover = new MovementStrategyContext(new Static());
  }

  @Override
  public int getState() {
    return myState;
  }

  @Override
  public Position step() {
    return myMover.move(new Position(getPosition()[0], getPosition()[1]));
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
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
    return PELLET_POINT;
  }
}

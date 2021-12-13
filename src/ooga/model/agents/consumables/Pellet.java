package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.Static;
import ooga.model.util.Position;

/**
 * Pellet class for Pacman.
 */
public class Pellet extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 2;
  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;
  private int myState;

  /**
   * pellet constructor
   *
   * @param x int x position
   * @param y int y position
   */
  public Pellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
    setStrategy(new Static());
  }

  @Override
  public int getState() {
    return myState;
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public void setState(int i) {
    myState = i;
  }

  @Override
  public int getConsumed() {
    myState = EATEN_STATE;
    updateConsumer();
    return PELLET_POINT;
  }
}

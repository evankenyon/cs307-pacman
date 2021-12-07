package ooga.model.agents.consumables;

import ooga.model.GameState;
import ooga.model.agents.AbstractAgent;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class pellet extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 2;

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;
  private static final Logger LOG = LogManager.getLogger(pellet.class);

  private int myState;

  /**
   * abstract constructor for cell
   *
   * @param x int x position
   * @param y int y position
   */
  public pellet(int x, int y) {
    super(x, y);
    myState = UNEATEN_STATE;
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

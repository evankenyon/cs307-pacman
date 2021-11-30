package ooga.model.agents.consumables;

import ooga.model.agents.AbstractAgent;
import ooga.model.agents.players.Pacman;
import ooga.model.interfaces.Agent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class pellet extends AbstractAgent implements Consumable {

  private final static int PELLET_POINT = 2;

  private final static int EATEN_STATE = 0;
  private final static int UNEATEN_STATE = 1;
  private static final Logger LOG = LogManager.getLogger(pellet.class);

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
    return myMover.move(new Position(getPosition().getCoords()[0], getPosition().getCoords()[1]));
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public int consume(Consumable agent) {
    return 0;
  }

  @Override
  public void setState(int i) {
    myState = i;
  }


  @Override
  public void getConsumed() {
    myState = EATEN_STATE;
    //update score
    updateConsumer();
  }

  @Override
  public void applyEffects(Pacman pacman) {
  }

  @Override
  public int applyPoints() {
    return PELLET_POINT;
  }
}

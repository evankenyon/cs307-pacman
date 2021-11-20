package ooga.model.agents.players;

import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Consumable;
import ooga.model.movement.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pacman extends AbstractAgent {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int SUPER_STATE = 2;

  private int myState;
  private MovementStrategyContext myMover;
  private static final Logger LOG = LogManager.getLogger(Pacman.class);

  public Pacman(int x, int y) {
    super(x, y);
    myState = ALIVE_STATE;
    myMover = new MovementStrategyContext(new Controllable());
  }

  @Override
  public int getState() {
    return myState;
  }

  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  public Position step() {
    LOG.info(String.format("%d, %d", getPosition().getCoords()[0], getPosition().getCoords()[1]));
    return myMover.move(getPosition());
  }

  public int consume(Consumable agent) {
    agent.agentReact();
    agent.applyEffects();
    return agent.applyPoints();
  }

}

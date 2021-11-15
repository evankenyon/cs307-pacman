package ooga.model.agents.players;

import ooga.model.agents.AbstractAgent;
import ooga.model.movement.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;

public class Pacman extends AbstractAgent {

  private final static int DEAD_STATE = 0;
  private final static int ALIVE_STATE = 1;
  private final static int SUPER_STATE = 2;

  private int myState;
  private Position myPosition;
  private MovementStrategyContext myMover;

  public Pacman(int x, int y){
    super(x,y);
    myState = ALIVE_STATE;
    myMover = new MovementStrategyContext(new Controllable());
  }

  public void step() {
    Position oldPosition = myPosition;
    myPosition = myMover.move(oldPosition);
  }
}

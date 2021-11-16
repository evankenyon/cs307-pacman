package ooga.model.agents.players;
import ooga.model.agents.AbstractAgent;
import ooga.model.interfaces.Controllable;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.util.Position;

public class Pacman extends AbstractAgent implements Controllable {

  public final static int DEAD_STATE = 0;
  public final static int ALIVE_STATE = 1;
  public final static int SUPER_STATE = 2;

  private String currentDirection;
  private int myState;
  private Position myPosition;
  private String myDirection;
  private MovementStrategyContext myMover;

  public Pacman(int x, int y) {
    super(x, y, "PACMAN");
    myState = ALIVE_STATE;
  }

  public void step() {
    Position oldPosition = myPosition;
  }

  @Override
  public void setDirection(String direction) {
    currentDirection = direction;
  }
}

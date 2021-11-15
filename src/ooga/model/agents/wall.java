package ooga.model.agents;

import java.util.function.Consumer;
import ooga.model.interfaces.AbstractAgent;
import ooga.model.interfaces.Agent;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class wall extends AbstractAgent {

  private static final int UNPASSABLE = 0;
  private static final int PASSABLE = 1;

  private int myState;
  private Position myPosition;
  private MovementStrategyContext myMover;

  public wall(int x, int y, int state) {
    super( x, y);
    myState = PASSABLE;
    myMover = new MovementStrategyContext(new Static());
  }

  @Override
  public void step() {
    myMover.move(myPosition);
  }
}

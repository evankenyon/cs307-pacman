package ooga.model.agents;


import ooga.model.agents.AbstractAgent;
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
    super(x, y, "WALL");
    myState = PASSABLE;
    myMover = new MovementStrategyContext(new Static());
  }

  @Override
  public void step() {
    myMover.move(myPosition);
  }
}

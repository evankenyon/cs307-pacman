package ooga.model.agents;


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
  public Position step() {
    return myMover.move(myPosition);
  }

  @Override
  public int[] getPosition() {
    return myPosition.getCoords();
  }

  @Override
  public int getState() {
    return myState;
  }
}

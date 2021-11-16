package ooga.model.agents;


import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class wall extends AbstractAgent {

  private static final int UNPASSABLE = 0;
  private static final int PASSABLE = 1;

  private int myState;
  private MovementStrategyContext myMover;

  public wall(int x, int y) {
    super(x, y);
    myState = PASSABLE;
    myMover = new MovementStrategyContext(new Static());
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
  public int getState() {
    return myState;
  }

}

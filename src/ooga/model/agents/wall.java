package ooga.model.agents;


import ooga.model.GameState;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class wall extends AbstractAgent {

  public static final int UNPASSABLE = 0;
  public static final int PASSABLE = 1;

  private int myState;
  private MovementStrategyContext myMover;

  public wall(int x, int y) {
    super(x, y);
    myState = UNPASSABLE;
    myMover = new MovementStrategyContext();
    setStrategy(new Static());
  }

  @Override
  public Position getNextMove(GameState state) {
    return myMover.move(state, getPosition());
  }

  @Override
  public void setCoords(Position newPosition) {
    setPosition(newPosition.getCoords());
  }

  @Override
  public void setDirection(String direction) {
  }

  @Override
  public void setState(int i) {
    myState = i;
  }

  @Override
  public int getState() {
    return myState;
  }

}

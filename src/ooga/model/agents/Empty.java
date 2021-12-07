package ooga.model.agents;


import ooga.model.GameState;
import ooga.model.movement.MovementStrategyContext;
import ooga.model.movement.Static;
import ooga.model.util.Position;

public class Empty extends AbstractAgent {

  public static final int EMPTY = 0;

  private int myState;
  private MovementStrategyContext myMover;

  public Empty(int x, int y) {
    super(x, y);
    myState = EMPTY;
    myMover = new MovementStrategyContext();
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
